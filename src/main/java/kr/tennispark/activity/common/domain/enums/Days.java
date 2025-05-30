package kr.tennispark.activity.common.domain.enums;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import kr.tennispark.activity.common.domain.exception.InvalidActivityTimeException;

public enum Days {

    MON, TUE, WED, THU, FRI, SAT, SUN;

    public static List<Days> from(List<String> days) {
        if (days == null || days.isEmpty()) {
            throw new InvalidActivityTimeException("활동 요일은 필수 입력값입니다.");
        }

        try {
            return days.stream()
                    .map(day -> Days.valueOf(day.toUpperCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidActivityTimeException("유효하지 않은 활동 요일이 포함되어 있습니다.");
        }
    }

    public DayOfWeek toDayOfWeek() {
        return switch (this) {
            case MON -> DayOfWeek.MONDAY;
            case TUE -> DayOfWeek.TUESDAY;
            case WED -> DayOfWeek.WEDNESDAY;
            case THU -> DayOfWeek.THURSDAY;
            case FRI -> DayOfWeek.FRIDAY;
            case SAT -> DayOfWeek.SATURDAY;
            case SUN -> DayOfWeek.SUNDAY;
        };
    }
}
