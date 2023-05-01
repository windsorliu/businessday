package com.windsor.businessday.entity;

import lombok.Data;

import java.util.List;

@Data
public class Results {
    private String currency;
    private List<ResultsParam> results;
}
