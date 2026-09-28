package com.uunnm.titletwo.common.log.aspect;

import com.uunnm.titletwo.business.auth.entity.UserDetailsImpl;
import com.uunnm.titletwo.common.log.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 统一操作日志切面。*
 * 设计要点（对应实施方案已确认的决策）：
 * - 在 joinPoint.proceed() 正常返回之后才组装并写日志；业务方法抛异常时不记录，
 *   保证日志只反映"实际生效"的操作。
 * - 日志写入与业务操作共享同一事务，不做 REQUIRES_NEW 隔离。
 * - 写日志的异常被 try-catch 吞掉，只打印 error 级别日志，绝不影响业务方法本身
 *   或导致业务事务回滚。
 * - objectIdExpr / beforeExpr / afterExpr 均只从方法入参中取值，不反查数据库。
 * - 无登录上下文时（SecurityContextHolder 拿不到 Authentication）直接跳过记录，
 *   不抛异常，覆盖模拟接口等无用户会话的调用路径。
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class OperationLogAspect {

    private final OperationLogService operationLogService;

    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(operationLogAnno)")
    public Object around(ProceedingJoinPoint joinPoint,
                         com.uunnm.titletwo.common.log.annotation.OperationLog operationLogAnno) throws Throwable {
        // 业务方法先正常执行；若抛异常直接向上传播，不记录日志
        Object result = joinPoint.proceed();

        try {
            recordLog(joinPoint, operationLogAnno);
        } catch (Exception e) {
            // 日志记录失败绝不能影响业务结果，仅打印错误日志
            log.error("操作日志记录失败，method={}, type={}",
                    joinPoint.getSignature().toShortString(), operationLogAnno.type(), e);
        }

        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint,
                           com.uunnm.titletwo.common.log.annotation.OperationLog anno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            // 无登录上下文（如模拟接口路径）不记录，由业务代码自行处理
            return;
        }

        EvaluationContext evalContext = buildEvaluationContext(joinPoint);

        com.uunnm.titletwo.common.log.entity.OperationLog entity =
                new com.uunnm.titletwo.common.log.entity.OperationLog();
        entity.setOperatorId(userDetails.getId());
        entity.setOperatorName(userDetails.getUsername());
        entity.setOperationType(anno.type());
        entity.setObjectType(anno.objectType());
        entity.setObjectId(evalToString(evalContext, anno.objectIdExpr()));
        entity.setBeforeSummary(evalToString(evalContext, anno.beforeExpr()));
        entity.setAfterSummary(evalToString(evalContext, anno.afterExpr()));
        entity.setOperationTime(LocalDateTime.now());

        operationLogService.save(entity);
    }

    private EvaluationContext buildEvaluationContext(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        StandardEvaluationContext context = new StandardEvaluationContext();
        String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
        if (paramNames != null) {
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
        }
        return context;
    }

    private String evalToString(EvaluationContext context, String expr) {
        if (expr == null || expr.isBlank()) {
            return null;
        }
        try {
            Expression expression = expressionParser.parseExpression(expr);
            Object value = expression.getValue(context);
            return value == null ? null : String.valueOf(value);
        } catch (Exception e) {
            log.warn("操作日志 SpEL 表达式求值失败：{}", expr, e);
            return null;
        }
    }
}