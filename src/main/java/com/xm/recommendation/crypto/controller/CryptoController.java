package com.xm.recommendation.crypto.controller;

import com.xm.recommendation.crypto.model.CryptoData;
import com.xm.recommendation.crypto.model.CryptoEnum;
import com.xm.recommendation.crypto.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @GetMapping(path = "/oldest/{symbol}")
    public ResponseEntity<CryptoData> getOldest(@PathVariable CryptoEnum symbol) {
        Optional<CryptoData> oldestData = cryptoService.getOldest(symbol);
        return oldestData.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/newest/{symbol}")
    public ResponseEntity<CryptoData> getNewest(@PathVariable CryptoEnum symbol) {
        Optional<CryptoData> newestData = cryptoService.getNewest(symbol);
        return newestData.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/min/{symbol}")
    public ResponseEntity<CryptoData> getMinPrice(@PathVariable CryptoEnum symbol) {
        Optional<CryptoData> dataWithMinPrice = cryptoService.getCryptoWithMinPrice(symbol);
        return dataWithMinPrice.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/min/{symbol}/{date}")
    public ResponseEntity<CryptoData> getCryptoWithMinPriceForGivenMonth(@PathVariable CryptoEnum symbol, @PathVariable String date) {
        Optional<CryptoData> dataWithMinPriceForGivenMonth
                = cryptoService.getCryptoWithMinPriceForGivenMonth(symbol, LocalDate.parse(date).atStartOfDay());
        return dataWithMinPriceForGivenMonth.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/max/{symbol}")
    public ResponseEntity<CryptoData> getMaxPrice(@PathVariable CryptoEnum symbol) {
        Optional<CryptoData> dataWithMaxPrice = cryptoService.getCryptoWithMaxPrice(symbol);
        return dataWithMaxPrice.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/max/{symbol}/{date}")
    public ResponseEntity<CryptoData> getCryptoWithMaxPriceForGivenMonth(@PathVariable CryptoEnum symbol, @PathVariable String date) {
        Optional<CryptoData> dataWithMaxPriceForGivenMonth =
                cryptoService.getCryptoWithMaxPriceForGivenMonth(symbol, LocalDate.parse(date).atStartOfDay());
        return dataWithMaxPriceForGivenMonth.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/normalized")
    public List<CryptoData> getNormalized() {
        return cryptoService.getNormalized();
    }

    @GetMapping("/highestNormalizedRange/{date}")
    public ResponseEntity<CryptoData> getHighestNormalizedRangeForASpecificDay(@PathVariable String date) {
        Optional<CryptoData> highestNormalizedRange = cryptoService.getHighestNormalizedRangeForASpecificDay(LocalDate.parse(date));
        return highestNormalizedRange.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{symbol}/{date}")
    public List<CryptoData> getAllCryptosFromDateToNow(@PathVariable CryptoEnum symbol, @PathVariable String date) {
        return cryptoService.getAllCryptosFromDateToNow(symbol, LocalDate.parse(date).atStartOfDay());
    }
}
