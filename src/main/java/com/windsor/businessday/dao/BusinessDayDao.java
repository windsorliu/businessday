package com.windsor.businessday.dao;

import java.io.FileNotFoundException;
import org.json.JSONException;
import com.windsor.businessday.entity.CurrencyList;
import com.windsor.businessday.entity.HolidayTable;

public interface BusinessDayDao {

    CurrencyList getCurrencyList(String year) throws JSONException, FileNotFoundException;

    HolidayTable getHolidaysTable(String year, String currency, int index) throws JSONException, FileNotFoundException;
}
