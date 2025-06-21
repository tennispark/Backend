package kr.tennispark.activity.common.domain.vo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import kr.tennispark.activity.common.domain.enums.Days;

public record WeekPeriod(LocalDate start) {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");
    private static final DayOfWeek WEEK_START = DayOfWeek.FRIDAY;

    public static WeekPeriod current() {
        LocalDate today = LocalDate.now(ZONE_ID);
        LocalDate start = today.with(TemporalAdjusters.previousOrSame(WEEK_START));
        return new WeekPeriod(start);
    }

    public LocalDate toDate(Days day) {
        return start.with(TemporalAdjusters.nextOrSame(day.toDayOfWeek()));
    }

    public boolean contains(LocalDate date) {
        return !date.isBefore(start) && !date.isAfter(end());
    }

    private LocalDate end() {
        return start.plusDays(6);
    }
}

