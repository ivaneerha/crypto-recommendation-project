package com.task.crypto.recommendation.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.task.crypto.recommendation.csv.CsvCryptoData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvDataService {
    public List<CsvCryptoData> readCsvData(String fileName) throws IOException {
        List<CsvCryptoData> cryptoData = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Paths.get(fileName));
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                CsvCryptoData data = new CsvCryptoData();
                data.setDateTime(convertStringTimestampToLocalDateTime(line[0]));
                data.setSymbol(line[1]);
                data.setPrice(Double.parseDouble(line[2]));

                cryptoData.add(data);
            }
        } catch (CsvValidationException e) {
            // handle the exception here TODO
            e.printStackTrace();
        }

        return cryptoData;
    }

    private LocalDateTime convertStringTimestampToLocalDateTime(String timestampString) {
        long timestamp = Long.parseLong(timestampString);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC);
    }
}
