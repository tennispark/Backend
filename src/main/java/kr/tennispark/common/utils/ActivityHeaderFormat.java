package kr.tennispark.common.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import kr.tennispark.activity.common.domain.Activity;

public final class ActivityHeaderFormat {
    private static final Locale KO = Locale.KOREAN;

    private ActivityHeaderFormat() {
    }

    public static String header(Activity a) {
        LocalDate d = a.getDate();
        LocalTime t = a.getScheduledTime().getBeginAt();
        return String.format(
                "[%02d월 %02d일 %02d시 %s %s]",
                d.getMonthValue(),
                d.getDayOfMonth(),
                t.getHour(),
                a.getPlace().getName(),
                a.getCourtName()
        );
    }
}
