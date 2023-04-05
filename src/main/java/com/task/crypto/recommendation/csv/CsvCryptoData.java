package com.task.crypto.recommendation.csv;

import java.time.LocalDateTime;

public class CsvCryptoData {
    private LocalDateTime dateTime;
    private String symbol;

    private double price;

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.dateTime + " - " + this.symbol + " - " + this.price + "\n";
    }
}
