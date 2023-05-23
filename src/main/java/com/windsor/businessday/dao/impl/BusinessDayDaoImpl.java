package com.windsor.businessday.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.windsor.businessday.dao.BusinessDayDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Repository;

import com.windsor.businessday.entity.CurrencyList;
import com.windsor.businessday.entity.HolidayTable;

@Repository
public class BusinessDayDaoImpl implements BusinessDayDao {

    public CurrencyList getCurrencyList(String year) throws JSONException, FileNotFoundException {
        CurrencyList currencyList = new CurrencyList();
        List<String> list = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(new JSONTokener(
                new FileReader("src/main/resources/static/" + year + ".json")));
        JSONArray holidayTableList = jsonObject.getJSONArray("HolidayTableList");

        for (int i = 0; i < holidayTableList.length(); i++) {
            JSONObject obj = holidayTableList.getJSONObject(i); // 取出HolidayTableList中的各個Object
            list.add(obj.getString("Currency"));
        }

        currencyList.setYear(year);
        currencyList.setCurrencyList(list);

        return currencyList;
    }

    public HolidayTable getHolidaysTable(String year, String currency, int index)
            throws JSONException, FileNotFoundException {

        JSONObject jsonObject = new JSONObject(new JSONTokener(
                new FileReader("src/main/resources/static/" + year + ".json")));
        JSONArray holidayTableList = jsonObject.getJSONArray("HolidayTableList");
        JSONObject obj = holidayTableList.getJSONObject(index);
        HolidayTable holidayTable = new HolidayTable();

        JSONArray holidaysJson = obj.getJSONArray("holidays");
        JSONArray weekendsJson = obj.getJSONArray("weekends");
        JSONArray workdaysJson;

        Object object = obj.get("workdays");
        if (object instanceof JSONArray) {
            workdaysJson = obj.getJSONArray("workdays");
        } else {
            workdaysJson = new JSONArray();
        }

        List<String> holidays = new ArrayList<>();
        List<Integer> weekends = new ArrayList<>();
        List<String> workdays = new ArrayList<>();

        for (int i = 0; i < holidaysJson.length(); i++) {
            holidays.add(holidaysJson.getString(i).substring(0, 10));
        }
        for (int i = 0; i < weekendsJson.length(); i++) {
            weekends.add(Integer.parseInt(weekendsJson.getString(i)) + 1);
            // +1是為了對應Calendar.DAY_OF_WEEK的格式
        }
        if (!workdaysJson.isEmpty()) {
            for (int i = 0; i < workdaysJson.length(); i++) {
                workdays.add(workdaysJson.getString(i).substring(0, 10));
            }
        }

        holidayTable.setHolidays(holidays);
        holidayTable.setWeekends(weekends);
        holidayTable.setWorkdays(workdays);

        return holidayTable;
    }
}