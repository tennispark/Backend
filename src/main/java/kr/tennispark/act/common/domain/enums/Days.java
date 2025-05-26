package kr.tennispark.act.common.domain.enums;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import kr.tennispark.act.common.domain.exception.InvalidActTimeException;

public enum Days {

    MON, TUE, WED, THU, FRI, SAT, SUN;

    public static List<Days> from(List<String> days) {
        if (days == null || days.isEmpty()) {
            throw new InvalidActTimeException("활동 요일은 필수 입력값입니다.");
        }

        try {
            return days.stream()
                    .map(day -> Days.valueOf(day.toUpperCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidActTimeException("유효하지 않은 활동 요일이 포함되어 있습니다.");
        }
    }
}