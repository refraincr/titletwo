package com.uunnm.titletwo.record.util;

import com.uunnm.titletwo.business.record.util.RecordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {RecordUtil.class})
public class RecordUtilTest {
    @Test
    public void extraKeywordsTest(){
        RecordUtil recordUtil = new RecordUtil();
        System.out.println(recordUtil.extraKeywords("我老公欠了外面一屁股债，还天天赌博，家里的钱都被他输光了。\n" +
                "我跟他吵了好几次，他扬言要杀我，还说要跳楼。\n" +
                "我现在很绝望，想离婚，但又担心孩子的抚养问题。"));
    }
}
