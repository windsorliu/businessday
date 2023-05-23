package com.windsor.businessday.controller;

import com.windsor.businessday.entity.CurrencyList;
import com.windsor.businessday.entity.Results;
import com.windsor.businessday.service.BusinessDayService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Results>> getResults(
            @RequestParam("year") String yearRequest,
            @RequestParam("currency") String currencyRequest)
            throws JSONException, FileNotFoundException, ParseException {

        String[] year = yearRequest.split(",");
        String[] currency = currencyRequest.split(",");
        List<Results> resultsList = new ArrayList<>();

        for (int i = 0; i < year.length; i++) {
            for (int j = 0; j < currency.length; j++) {
                Results results = businessDayService.getResults(year[i], currency[j]);
                resultsList.add(results);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultsList);
    }
}
