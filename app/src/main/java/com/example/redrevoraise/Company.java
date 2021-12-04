package com.example.redrevoraise;

import java.io.Serializable;

public class Company implements Serializable {
    private String ticker;
    private String currency;
    private String displayName;
    private String region;
    private String regularMarketPrice;
    private String regularMarketYest;
    private String regularMarketDayHigh;
    private String regularMarketDayLow;
    private String regularMarketVolume;
    private String regularMarketOpen;
    private String fiftyTwoWeekLow;
    private String fiftyTwoWeekHigh;

    public Company() {
    }

    public Company(String ticker, String currency, String displayName, String region, String regularMarketPrice, String regularMarketYest, String regularMarketDayHigh,
                   String regularMarketDayLow, String regularMarketVolume, String regularMarketOpen, String fiftyTwoWeekLow, String fiftyTwoWeekHigh) {
        this.ticker = ticker;
        this.currency = currency;
        this.displayName = displayName;
        this.region = region;
        this.regularMarketPrice = regularMarketPrice;
        this.regularMarketYest = regularMarketYest;
        this.regularMarketDayHigh = regularMarketDayHigh;
        this.regularMarketDayLow = regularMarketDayLow;
        this.regularMarketVolume = regularMarketVolume;
        this.regularMarketOpen = regularMarketOpen;
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setRegularMarketPrice(String regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }

    public String getRegularMarketYest() {
        return regularMarketYest;
    }

    public void setRegularMarketYest(String regularMarketYest) {
        this.regularMarketYest = regularMarketYest;
    }

    public String getRegularMarketDayHigh() {
        return regularMarketDayHigh;
    }

    public void setRegularMarketDayHigh(String regularMarketDayHigh) {
        this.regularMarketDayHigh = regularMarketDayHigh;
    }

    public String getRegularMarketDayLow() {
        return regularMarketDayLow;
    }

    public void setRegularMarketDayLow(String regularMarketDayLow) {
        this.regularMarketDayLow = regularMarketDayLow;
    }

    public String getRegularMarketVolume() {
        return regularMarketVolume;
    }

    public void setRegularMarketVolume(String regularMarketVolume) {
        this.regularMarketVolume = regularMarketVolume;
    }

    public String getRegularMarketOpen() {
        return regularMarketOpen;
    }

    public void setRegularMarketOpen(String regularMarketOpen) {
        this.regularMarketOpen = regularMarketOpen;
    }

    public String getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    public String getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }
}
