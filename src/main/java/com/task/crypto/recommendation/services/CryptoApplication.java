package com.task.crypto.recommendation.services;

import com.task.crypto.recommendation.csv.CsvCryptoData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class CryptoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoApplication.class, args);
		CsvDataService s = new CsvDataService();
		try {
			List<CsvCryptoData> l = s.readCsvData("C:\\Users\\Ivana_Vasileva\\Documents\\prices\\BTC_values.csv");
			System.out.println(l);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
