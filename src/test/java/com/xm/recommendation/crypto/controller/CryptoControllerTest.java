package com.xm.recommendation.crypto.controller;

import com.xm.recommendation.Application;
import com.xm.recommendation.crypto.model.CryptoData;
import com.xm.recommendation.crypto.model.CryptoEnum;
import com.xm.recommendation.crypto.service.CryptoService;
import com.xm.recommendation.parser.CsvParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class CryptoControllerTest {

    private final CsvParser csvParser = new CsvParser();

    @Spy
    private CryptoService service = new CryptoService(csvParser);

    @InjectMocks
    private CryptoController controller;

    @Test
    public void testGetOldestWithValidSymbolShouldReturn200() {
        ResponseEntity<CryptoData> response = controller.getOldest(CryptoEnum.DOGE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LocalDateTime.parse(("2022-01-01T05:00:00")), Objects.requireNonNull(response.getBody()).getDate());
    }

    @Test
    public void testGetOldestWithEmptySymbolShouldReturn404() {
        when(service.getOldest(null)).thenCallRealMethod();
        ResponseEntity<CryptoData> response = controller.getOldest(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetNewestWithValidSymbolShouldReturn200() {
        ResponseEntity<CryptoData> response = controller.getNewest(CryptoEnum.BTC);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LocalDateTime.parse(("2022-01-31T20:00:00")), Objects.requireNonNull(response.getBody()).getDate());
    }

    @Test
    public void testGetNewestWithEmptySymbolShouldReturn404() {
        when(service.getNewest(null)).thenCallRealMethod();
        ResponseEntity<CryptoData> response = controller.getNewest(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetMinWithValidSymbolShouldReturn200() {
        ResponseEntity<CryptoData> response = controller.getMinPrice(CryptoEnum.XRP);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0.5616, Objects.requireNonNull(response.getBody()).getPrice());
    }

    @Test
    public void testGetMinWithEmptySymbolShouldReturn404() {
        when(service.getCryptoWithMinPrice(null)).thenCallRealMethod();
        ResponseEntity<CryptoData> response = controller.getMinPrice(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetMinForGivenMonthWithValidSymbolShouldReturn200() {
        ResponseEntity<CryptoData> response = controller.getCryptoWithMinPriceForGivenMonth(CryptoEnum.XRP, "2022-01-02");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0.5616, Objects.requireNonNull(response.getBody()).getPrice());
    }

    @Test
    public void testGetMinForGivenMonthWithNoDataForDateAndSymbolShouldReturn404() {
        ResponseEntity<CryptoData> response = controller.getCryptoWithMinPriceForGivenMonth(CryptoEnum.XRP, "2022-02-02");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetMaxWithValidSymbolShouldReturn200() {
        ResponseEntity<CryptoData> response = controller.getMaxPrice(CryptoEnum.XRP);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0.8458, Objects.requireNonNull(response.getBody()).getPrice());
    }

    @Test
    public void testGetMaxWithEmptySymbolShouldReturn404() {
        when(service.getCryptoWithMaxPrice(null)).thenCallRealMethod();
        ResponseEntity<CryptoData> response = controller.getMaxPrice(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetMaxForGivenMonthWithValidSymbolShouldReturn200() {
        ResponseEntity<CryptoData> response = controller.getCryptoWithMaxPriceForGivenMonth(CryptoEnum.XRP, "2022-01-02");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0.8458, Objects.requireNonNull(response.getBody()).getPrice());
    }

    @Test
    public void testGetMaxForGivenMonthWithNoDataForDateAndSymbolShouldReturn404() {
        ResponseEntity<CryptoData> response = controller.getCryptoWithMaxPriceForGivenMonth(CryptoEnum.XRP, "2022-02-02");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetNormalizedShouldReturnNotEmptyList() {
        List<CryptoData> response = controller.getNormalized();
        assertFalse(response.isEmpty());
    }

    @Test
    public void testGetHighestNormalizedRangeWithValidDataForDateShouldReturn200() {
        ResponseEntity<CryptoData> response = controller.getHighestNormalizedRangeForASpecificDay("2022-01-03");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0.9872923755867014, Objects.requireNonNull(response.getBody()).getNormalisedRange());
    }

    @Test
    public void testGetHighestNormalizedRangeWithNoDataForDateShouldReturn404() {
        ResponseEntity<CryptoData> response = controller.getHighestNormalizedRangeForASpecificDay("2022-03-03");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllCryptosFromDateToNowShouldReturnNotEmptyList() {
        List<CryptoData> response = controller.getAllCryptosFromDateToNow(CryptoEnum.XRP, "2022-01-02");
        assertFalse(response.isEmpty());
    }
}
