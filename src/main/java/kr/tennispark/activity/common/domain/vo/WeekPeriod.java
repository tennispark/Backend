package kr.tennispark.activity.common.domain.vo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import kr.tennispark.activity.common.domain.enums.Days;

public record WeekPeriod(LocalDate monday) {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    public static WeekPeriod current() {
        LocalDate today = LocalDate.now(ZONE_ID);
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return new WeekPeriod(monday);
    }
    
    public LocalDate toDate(Days day) {
        return monday.with(TemporalAdjusters.nextOrSame(day.toDayOfWeek()));
    }

    public boolean contains(LocalDate date) {
        return !date.isBefore(monday) && !date.isAfter(sunday());
    }

    private LocalDate sunday() {
        return monday.plusDays(6);
    }
}

