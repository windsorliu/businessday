package com.windsor.businessday.entity;

import lombok.Data;

import java.util.List;

@Data
public class YearAndCurrency {
    List<String> year;
    List<String> currency;
}
