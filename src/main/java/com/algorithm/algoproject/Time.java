package com.algorithm.algoproject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Time {

    private final static int SEC = 60;
    private final static int MIN = 60;
    private final static int HOUR = 24;
    private final static int MONTH = 12;


    public static String txtDate(LocalDateTime tempDate) {
        int lastMonth = getLastMonth();

        LocalDateTime now = LocalDateTime.now();

        long diffTime = tempDate.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

        String msg = null;
        if (diffTime < SEC) {
            return diffTime + "초전";
        }
        diffTime = diffTime / SEC;
        if (diffTime < MIN) {
            return diffTime + "분 전";
        }
        diffTime = diffTime / MIN;
        if (diffTime < HOUR) {
            return diffTime + "시간 전";
        }
        diffTime = diffTime / HOUR;
        if (diffTime < lastMonth) {
            return diffTime + "일 전";
        }
        diffTime = diffTime / lastMonth;
        if (diffTime < MONTH) {
            return diffTime + "개월 전";
        }

        diffTime = diffTime / MONTH;
        return diffTime + "년 전";
    }

    private static int getLastMonth() {
        LocalDateTime now = LocalDateTime.now();
        YearMonth yearMonth = YearMonth.from(now);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        return lastDayOfMonth.getDayOfMonth();

    }
}