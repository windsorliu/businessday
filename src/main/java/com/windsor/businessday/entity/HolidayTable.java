package com.windsor.businessday.entity;

import lombok.Data;

import java.util.List;

@Data
public class HolidayTable {
    private List<String> holidays;
    private List<Integer> weekends;
    private List<String> workdays;
}
