package com.wirebarley.kkstodolist.currencyExchange;

import lombok.Data;

import java.util.Map;

@Data
public class CurrencyApiDto {

    private boolean success;
    private String terms;
    private String privacy;
    private long timestamp;
    private String source;
    private Map<String, Double> quotes;

    private Map<String, Object> error;

}
