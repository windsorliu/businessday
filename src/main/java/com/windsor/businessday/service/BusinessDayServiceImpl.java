package com.windsor.businessday.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.windsor.businessday.dao.BusinessDayDao;
import com.windsor.businessday.entity.CurrencyList;
import com.windsor.businessday.entity.HolidayTable;
import com.windsor.businessday.entity.Results;
import com.windsor.businessday.entity.ResultsParam;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BusinessDayServiceImpl implements BusinessDayService {

    private final static Logger log = LoggerFactory.getLogger(BusinessDayServiceImpl.class);

    @Autowired
    private BusinessDayDao businessDayDao;

    public CurrencyList getCurrencyList(String year) throws JSONException, FileNotFoundException {
        return businessDayDao.getCurrencyList(year);
    }

    public int getCurrencyIndex(String year, String currency) throws JSONException, FileNotFoundException {

        CurrencyList currencyList = businessDayDao.getCurrencyList(year);
        List<String> list = currencyList.getCurrencyList();

        int index = list.indexOf(currency.toUpperCase());

        return index;
    }

    public Results getResults(String year, String currency)
            throws JSONException, FileNotFoundException, ParseException {

        int index = getCurrencyIndex(year, currency);

        if (index == -1) {
            log.warn("Currency not found : {}", currency);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        HolidayTable holidayTable = businessDayDao.getHolidaysTable(year, currency, index);
        List<String> holidays = holidayTable.getHolidays();
        List<Integer> weekends = holidayTable.getWeekends();
        List<String> workdays = holidayTable.getWorkdays();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(year + "-01-01");
        Calendar businessDay = Calendar.getInstance();
        businessDay.setTime(date);
        Calendar holidaysCalendar = Calendar.getInstance();
        Calendar workdaysCalendar = Calendar.getInstance();

        Results results = new Results();
        List<ResultsParam> list = new ArrayList<>();

        int parseYear = Integer.parseInt(year);

        int daysOfYear = 0;

        if (parseYear % 4 == 0) {
            daysOfYear = 366;
        } else {
            daysOfYear = 365;
        }

        for (int i = 0; i < daysOfYear; i++) {
            ResultsParam resultsParam = new ResultsParam();
            resultsParam.setIsBusinessDay("Y");

            for (int j = 0; j < weekends.size(); j++) {
                if (businessDay.get(Calendar.DAY_OF_WEEK) == weekends.get(j)) {
                    resultsParam.setIsBusinessDay("N");
                    break;
                    // 如果是一般例假日就直接跳出
                }
            }

            if (!holidays.isEmpty()) {
                for (int j = 0; j < holidays.size(); j++) {
                    holidaysCalendar.setTime(sdf.parse(holidays.get(j)));
                    if (businessDay.equals(holidaysCalendar)) {
                        resultsParam.setIsBusinessDay("N");
                        holidays.remove(j);
                        break;
                        // 如果是國定假日就直接跳出
                    }
                }
            }

            if (!workdays.isEmpty()) {
                for (int j = 0; j < workdays.size(); j++) {
                    workdaysCalendar.setTime(sdf.parse(workdays.get(j)));
                    if (businessDay.equals(workdaysCalendar)) {
                        resultsParam.setIsBusinessDay("Y");
                        workdays.remove(j);
                        break;
                    }
                }
            }

            resultsParam.setDate(sdf.format(businessDay.getTime()));
            list.add(resultsParam);
            businessDay.add(Calendar.DATE, 1);
        }

        results.setResults(list);
        results.setCurrency(currency.toUpperCase());

        return results;
    }
}