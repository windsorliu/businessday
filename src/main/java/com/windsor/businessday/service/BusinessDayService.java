package com.windsor.businessday.service;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.json.JSONException;

import com.windsor.businessday.entity.CurrencyList;
import com.windsor.businessday.entity.Results;

public interface BusinessDayService {

    CurrencyList getCurrencyList(String year) throws JSONException, FileNotFoundException;

    int getCurrencyIndex(String year, String currency) throws JSONException, FileNotFoundException;

    Results getResults(String year, String currency) throws JSONException, FileNotFoundException, ParseException;
}
