package com.xm.recommendation.crypto.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CryptoData {

    private LocalDateTime date;

    private CryptoEnum symbol;

    private double price;

    private double normalisedRange;

}
