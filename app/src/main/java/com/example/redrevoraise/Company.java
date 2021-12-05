package com.example.redrevoraise;

import java.io.Serializable;

public class Company implements Serializable {
    private String ticker;
    private String region;
    private String priceToday;
    private String priceYest;

    public Company() {
    }

    public Company(String ticker, String region, String priceToday, String priceYest) {
        this.ticker = ticker;
        this.region = region;
        this.priceToday = priceToday;
        this.priceYest = priceYest;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPriceToday() {
        return priceToday;
    }

    public void setPriceToday(String priceToday) {
        this.priceToday = priceToday;
    }

    public String getPriceYest() {
        return priceYest;
    }

    public void setPriceYest(String priceYest) {
        this.priceYest = priceYest;
    }
}
