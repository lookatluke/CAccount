/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 *
 * @author trito
 */
public class CalendarUtil {

    Calendar calendar = Calendar.getInstance();

    public CalendarUtil() {
    }

    public int getCurrentWeek() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("w");
        return Integer.parseInt(date.format(dtf));
    }

    public String getToday() {
        String today = " (" + getYear() + "년 " + getMonth() + "월 " + getDate() + "일)";
        return today;
    }

    public int getYear() {
        return calendar.get(1);
    }

    public int getMonth() {
        return calendar.get(2) + 1;
    }

    public int getDate() {
        return calendar.get(5);
    }

}
