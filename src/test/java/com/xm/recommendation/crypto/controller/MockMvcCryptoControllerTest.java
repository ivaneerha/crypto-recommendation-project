package com.xm.recommendation.crypto.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcCryptoControllerTest {

    private static final String ERROR_MSG = "This crypto is not supported yet";
    private static final String DATE_ERROR_MSG = "Invalid date format. Please use the format yyyy-MM-dd.";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetOldestWithInvalidSymbolShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/oldest/invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MSG));
    }

    @Test
    public void testGetOldestWithValidSymbolShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/oldest/DOGE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2022-01-01T05:00:00"))
                .andExpect(jsonPath("$.symbol").value("DOGE"))
                .andExpect(jsonPath("$.price").value(0.1702));
    }

    @Test
    public void testGetNewestWithInvalidSymbolShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/newest/invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MSG));
    }

    @Test
    public void testGetNewestWithValidSymbolShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/newest/BTC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2022-01-31T20:00:00"))
                .andExpect(jsonPath("$.symbol").value("BTC"))
                .andExpect(jsonPath("$.price").value(38415.79));
    }

    @Test
    public void testGetMinWithInvalidSymbolShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/min/invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MSG));
    }

    @Test
    public void testGetMinWithValidSymbolShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/min/XRP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("XRP"))
                .andExpect(jsonPath("$.price").value(0.5616));
    }

    @Test
    public void testGetMinForGivenMonthWithInvalidDateShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/min/XRP/invalid_date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(DATE_ERROR_MSG));
    }

    @Test
    public void testGetMinForGivenMonthWithInvalidSymbolShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/min/invalid/2022-01-02"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MSG));
    }

    @Test
    public void testGetMinForGivenMonthWithValidDateAndSymbolShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/min/XRP/2022-01-02"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("XRP"))
                .andExpect(jsonPath("$.price").value(0.5616));
    }

    @Test
    public void testGetMinForGivenMonthWithNoDataForThisDateAndSymbolShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/crypto/min/XRP/2022-02-02"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMaxWithInvalidSymbolShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/min/invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MSG));
    }

    @Test
    public void testGetMaxWithValidSymbolShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/max/XRP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2022-01-01T21:00:00"))
                .andExpect(jsonPath("$.symbol").value("XRP"))
                .andExpect(jsonPath("$.price").value(0.8458))
                .andExpect(jsonPath("$.normalisedRange").value(1.5020158926608481E-5));
    }

    @Test
    public void testGetMaxForGivenMonthWithInvalidDateShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/min/XRP/invalid_date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(DATE_ERROR_MSG));
    }

    @Test
    public void testGetMaxForGivenMonthWithInvalidSymbolShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/max/invalid/2022-01-02"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MSG));
    }

    @Test
    public void testGetMaxForGivenMonthWithValidDateAndSymbolShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/min/XRP/2022-01-02"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("XRP"))
                .andExpect(jsonPath("$.price").value(0.5616));
    }

    @Test
    public void testGetMaxForGivenMonthWithNoDataForThisDateAndSymbolShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/crypto/max/XRP/2022-02-02"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetNormalizedShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/normalized"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].symbol").value(hasItems("BTC", "DOGE")))
                .andExpect(jsonPath("$[*].price").value(hasItems(47722.66, 47336.98)));
    }

    @Test
    public void testGetHighestNormalizedRangeWithDataForThisDateShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/highestNormalizedRange/2022-01-03"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("BTC"))
                .andExpect(jsonPath("$.normalisedRange").value(0.9872923755867014));
    }

    @Test
    public void testGetHighestNormalizedRangeWithNoDataForThisDateShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/crypto/highestNormalizedRange/2022-03-03"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetHighestNormalizedRangeWithInvalidDataForDateShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/crypto/highestNormalizedRange/invalid_date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(DATE_ERROR_MSG));
    }

    @Test
    public void testGetAllCryptosFromDateToNowShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/crypto/DOGE/2020-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].symbol").value(hasItems("DOGE")))
                .andExpect(jsonPath("$[*].price").value(hasItems(0.1702)));
    }

    @Test
    public void testGetAllCryptosFromDateToNowWithInvalidDataForDateShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/crypto/DOGE/invalid_date"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(DATE_ERROR_MSG));
    }

    @Test
    public void testGetAllCryptosFromDateToNowWithInvalidSymbolShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/crypto/invalid/2020-01-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ERROR_MSG));
    }
}