package kr.tennispark.common.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public final class KoreanFormat {
    private static final Locale KO = Locale.KOREAN;

    public static String date(LocalDate d) {
        String base = String.format("%04d년 %02d월 %02d일",
                d.getYear(), d.getMonthValue(), d.getDayOfMonth());
        String dow = d.getDayOfWeek().getDisplayName(TextStyle.NARROW, KO);
        return base + "(" + dow + ")";
    }

    public static String time(LocalTime t) {
        return String.format("%02d:%02d", t.getHour(), t.getMinute());
    }
}
