package com.xm.recommendation.crypto.service;

import com.xm.recommendation.crypto.model.CryptoData;
import com.xm.recommendation.crypto.model.CryptoEnum;
import com.xm.recommendation.parser.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CryptoService {

    private final CsvParser parser;

    private final List<CryptoData> cryptoDataList;

    @Autowired
    public CryptoService(CsvParser parser) {
        this.parser = parser;
        this.cryptoDataList = loadAllCryptos();
    }

    public Optional<CryptoData> getOldest(CryptoEnum symbol) {
        return cryptoDataList.stream()
                .filter(data -> data.getSymbol().equals(symbol))
                .min(Comparator.comparing(CryptoData::getDate));
    }

    public Optional<CryptoData> getNewest(CryptoEnum symbol) {
        return cryptoDataList.stream()
                .filter(data -> data.getSymbol().equals(symbol))
                .max(Comparator.comparing(CryptoData::getDate));
    }

    public Optional<CryptoData> getCryptoWithMinPrice(CryptoEnum symbol) {
        return cryptoDataList.stream()
                .filter(data -> data.getSymbol().equals(symbol))
                .min(Comparator.comparing(CryptoData::getPrice));
    }

    public Optional<CryptoData> getCryptoWithMinPriceForGivenMonth(CryptoEnum symbol, LocalDateTime date) {
        return cryptoDataList.stream()
                .filter(data -> data.getSymbol().equals(symbol))
                .filter(data -> data.getDate().getYear() == date.getYear()
                        && data.getDate().getMonth().equals(date.getMonth()))
                .min(Comparator.comparing(CryptoData::getPrice));
    }

    public Optional<CryptoData> getCryptoWithMaxPrice(CryptoEnum symbol) {
        return cryptoDataList.stream()
                .filter(data -> data.getSymbol().equals(symbol))
                .max(Comparator.comparing(CryptoData::getPrice));
    }

    public Optional<CryptoData> getCryptoWithMaxPriceForGivenMonth(CryptoEnum symbol, LocalDateTime date) {
        return cryptoDataList.stream()
                .filter(data -> data.getSymbol().equals(symbol))
                .filter(data -> data.getDate().getYear() == date.getYear()
                        && data.getDate().getMonth().equals(date.getMonth()))
                .max(Comparator.comparing(CryptoData::getPrice));
    }

    public List<CryptoData> getNormalized() {
        cryptoDataList.sort((c1, c2) -> Double.compare(c2.getNormalisedRange(), c1.getNormalisedRange()));

        return cryptoDataList;
    }

    public Optional<CryptoData> getHighestNormalizedRangeForASpecificDay(LocalDate date) {
        List<CryptoData> cryptosForTheDate = cryptoDataList.stream()
                .filter(data -> data.getDate().toLocalDate().isEqual(date))
                .toList();
        return cryptosForTheDate.stream().max(Comparator.comparingDouble(CryptoData::getNormalisedRange));
    }

    public List<CryptoData> getAllCryptosFromDateToNow(CryptoEnum symbol, LocalDateTime date) {
        return cryptoDataList.stream()
                .filter(c -> c.getSymbol().equals(symbol))
                .filter(c -> date.isBefore(c.getDate()))
                .sorted(Comparator.comparing(CryptoData::getDate))
                .toList();
    }

    private List<CryptoData> loadAllCryptos() {
        List<CryptoData> cryptoData = new ArrayList<>();

        for (CryptoEnum symbol : CryptoEnum.values()) {
            URL resource = CryptoService.class.getResource("/values/" + symbol + "_values.csv");
            if (resource != null) {
                cryptoData.addAll(parser.parse(resource.getPath()));
            }
        }

        calculateNormalisedRangeForAllValues(cryptoData);

        return cryptoData;
    }

    private void calculateNormalisedRangeForAllValues(List<CryptoData> cryptoDataList) {
        double minPrice = getMinPrice(cryptoDataList);
        double maxPrice = getMaxPrice(cryptoDataList);

        for(CryptoData data : cryptoDataList) {
            double price = data.getPrice();
            double normalizedRangeForACrypto = ((price-minPrice)/(maxPrice-minPrice));
            data.setNormalisedRange(normalizedRangeForACrypto);
        }
    }

    private double getMinPrice(List<CryptoData> cryptoDataList) {
        Optional<CryptoData> minData = cryptoDataList.stream().min(Comparator.comparing(CryptoData::getPrice));
        return minData.map(CryptoData::getPrice).orElse(Double.MIN_VALUE);
    }

    public double getMaxPrice(List<CryptoData> cryptoDataList) {
        Optional<CryptoData> maxData = cryptoDataList.stream().max(Comparator.comparing(CryptoData::getPrice));
        return maxData.map(CryptoData::getPrice).orElse(Double.MAX_VALUE);
    }
}
