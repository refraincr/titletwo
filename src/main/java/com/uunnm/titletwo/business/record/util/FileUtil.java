package com.uunnm.titletwo.business.record.util;

import com.uunnm.titletwo.business.auth.service.UserService;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtil {
    private final UserService userService;


    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Map<String, BiConsumer<InputRecord, String>> REQUIRED_FIELDS_SETTERS =
            Map.of(
                    "事件发生时间", (obj, val) ->
                        obj.setOccurredAt(LocalDateTime.parse(val, FORMATTER)),
                    "具体问题描述", InputRecord::setProblemDescription,
                    "当事人姓名", InputRecord::setPartyName,
                    "当事人联系电话", InputRecord::setPartyPhone,
                    "事件类别", InputRecord::setEventCategory,
                    "经办人姓名", InputRecord::setHandlerName,
                    "处理情况", InputRecord::setHandlingDescription,
                    "纠纷类型", InputRecord::setDisputeType
            );


    private static final Map<String, BiConsumer<InputRecord, String>> ELSE_FIELD_SETTERS =
            Map.of(
                    "当事人联系地址", InputRecord::setPartyAddress,
                    "当事人身份证号", InputRecord::setPartyIdCard,
                    "事件来源", InputRecord::setEventSource,
                    "处理期限", (obj, val) ->
                        obj.setHandlingDeadline(LocalDateTime.parse(val, FORMATTER)),
                    "是否办结", (obj, val)->
                        obj.setIsCompleted(Integer.valueOf(val)),
                    "备注", InputRecord::setRemark
            );

    public void processExcel(MultipartFile file, List<InputRecord> inputRecords) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Integer> headerMap = getHeaderMap(sheet.getRow(0));
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                if (validExcelData(row, headerMap)) {
                    InputRecord inputRecord = copyPropertiesExcelData(row, headerMap);
                    UserInfoVO userInfo = userService.getUserInfo();
                    inputRecord.setCreatedBy(String.valueOf(userInfo.getId()));
                    inputRecord.setModifiedBy(String.valueOf(userInfo.getId()));
                    inputRecords.add(inputRecord);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private boolean validExcelData(Row row, Map<String, Integer> headerMap) {
        for (Map.Entry<String, BiConsumer<InputRecord, String>> entry : REQUIRED_FIELDS_SETTERS.entrySet()) {
            String field = entry.getKey();
            String value =getCellValue(row, headerMap.get(field));
            if (value.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private InputRecord copyPropertiesExcelData(Row row, Map<String, Integer> headerMap) {
        InputRecord inputRecord = new InputRecord();
        // 必填字段
        REQUIRED_FIELDS_SETTERS.forEach((field, setter) -> {
            String cellValue = getCellValue(row, headerMap.get(field));
            setter.accept(inputRecord, cellValue);
        });

        // 非必填字段
        ELSE_FIELD_SETTERS.forEach((field, setter) -> {
            String cellValue = getCellValue(row, headerMap.get(field));
            if (!cellValue.isEmpty()) {
                setter.accept(inputRecord, cellValue);
            }
        });

        return inputRecord;
    }

    private Map<String, Integer> getHeaderMap(Row headerRow) {
        Map<String, Integer> headerMap = new HashMap<>();

        headerRow.forEach(cell -> {
            DataFormatter formatter = new DataFormatter();
            headerMap.put(formatter.formatCellValue(cell).trim(), cell.getColumnIndex());
        });

        return headerMap;
    }


    public void processCsv(MultipartFile file, List<InputRecord> inputRecords)  {
        try (
                Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setIgnoreEmptyLines(true)
                        .setTrim(true)
                        .get()
                        .parse(reader)
        ) {
            parser.forEach(record -> {
                if (validCsvData(record)) {
                    InputRecord inputRecord = copyPropertiesCsvData(record);
                    inputRecords.add(inputRecord);
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private boolean validCsvData(CSVRecord record) {
        for (Map.Entry<String, BiConsumer<InputRecord, String>> entry : REQUIRED_FIELDS_SETTERS.entrySet()) {
            String field = entry.getKey();
            if (!record.isMapped(field) || !record.isSet(field)) {
                return false;
            }
            String value = record.get(field);
            if (value == null || value.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private InputRecord copyPropertiesCsvData(CSVRecord record) {
        InputRecord inputRecord = new InputRecord();
        // 必填字段
        ELSE_FIELD_SETTERS.forEach((field, setter) -> {
            String value = record.get(field);
            setter.accept(inputRecord, value);
        });

        // 非必填字段
        ELSE_FIELD_SETTERS.forEach((field, setter) -> {
            if (record.isMapped(field) && record.isSet(field)) {
                String value = record.get(field);
                if (value != null && !value.isEmpty()) {
                    setter.accept(inputRecord, value);
                }
            }
        });

        return inputRecord;
    }
}
