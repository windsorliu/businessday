package com.windsor.businessday.entity;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyList {
    private String year;
    private List<String> currencyList;
}
