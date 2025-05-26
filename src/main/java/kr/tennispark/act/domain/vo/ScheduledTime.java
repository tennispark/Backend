package kr.tennispark.act.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import kr.tennispark.act.domain.exception.InvalidActTimeException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduledTime {

    @Column(nullable = false)
    private LocalTime beginAt;

    @Column(nullable = false)
    private LocalTime endAt;

    public static ScheduledTime of(LocalTime beginAt, LocalTime endAt) {
        validateActTime(beginAt, endAt);
        return new ScheduledTime(beginAt, endAt);
    }

    private static void validateActTime(LocalTime beginAt, LocalTime endAt) {
        if (beginAt == null || endAt == null) {
            throw new InvalidActTimeException("시작 시간과 종료 시간은 필수 입력값입니다.");
        }
        if (beginAt.isAfter(endAt)) {
            throw new InvalidActTimeException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }
    }
}
