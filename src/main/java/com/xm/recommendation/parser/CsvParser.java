package com.xm.recommendation.parser;

import com.xm.recommendation.crypto.model.CryptoData;
import com.xm.recommendation.crypto.model.CryptoEnum;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CsvParser {
    private final static char DELIMITER = ',';

    public List<CryptoData> parse(String filePath) {
        CSVFormat format = CSVFormat.DEFAULT
                .withDelimiter(DELIMITER)
                .withFirstRecordAsHeader();

        List<CryptoData> dataList = new ArrayList<>();
        try (FileReader fileReader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(fileReader, format)) {

            for (CSVRecord record : csvParser) {
                Map<String, String> recordMap = record.toMap();

                CryptoData data = mapCryptoData(recordMap);
                dataList.add(data);
            }
        } catch (IOException e) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error collecting the data");
        }

        return dataList;
    }

    private CryptoData mapCryptoData(Map<String, String> record) {
        return CryptoData.builder()
                .date(convertToLocalDateTime(record.get("timestamp")))
                .price(Double.parseDouble(record.get("price")))
                .symbol(CryptoEnum.valueOf(record.get("symbol")))
                .build();
    }

    private LocalDateTime convertToLocalDateTime(String timestamp) {
        long timestampLong = Long.parseLong(timestamp);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampLong), ZoneOffset.UTC);
    }
}
