package com.windsor.businessday.controller;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.windsor.businessday.entity.CurrencyList;
import com.windsor.businessday.entity.Results;
import com.windsor.businessday.entity.YearAndCurrency;
import com.windsor.businessday.service.BusinessDayService;

@RestController
@RequestMapping("/businessday")
public class BusinessDayController {

    @Autowired
    private BusinessDayService businessDayService;

    @GetMapping("/{year}")
    public ResponseEntity<CurrencyList> getCurrencyList(@PathVariable String year)
            throws JSONException, FileNotFoundException {

        CurrencyList currencyList = businessDayService.getCurrencyList(year);
        return ResponseEntity.status(HttpStatus.OK).body(currencyList);
    }

    @GetMapping("/{year}/{currency}")
    public ResponseEntity<Results> getResults(@PathVariable String year,
            @PathVariable String currency) throws JSONException, FileNotFoundException, ParseException {

        Results results = businessDayService.getResults(year, currency);

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @PostMapping
    public ResponseEntity<List<Results>> getMultipleResults(
            @RequestBody List<YearAndCurrency> yearAndCurrencyList)
            throws JSONException, FileNotFoundException, ParseException {

        List<Results> resultsList = new ArrayList<>();

        for (int i = 0; i < yearAndCurrencyList.size(); i++) {
            YearAndCurrency yearAndCurrency = yearAndCurrencyList.get(i);
            List<String> yearList = yearAndCurrency.getYear();
            List<String> currencyList = yearAndCurrency.getCurrency();

            for (int j = 0; j < yearList.size(); j++) {
                for (int k = 0; k < currencyList.size(); k++) {
                    Results results = getResults(yearList.get(j), currencyList.get(k)).getBody();
                    resultsList.add(results);
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultsList);
    }
}
