package kr.tennispark.activity.common.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import kr.tennispark.activity.common.domain.exception.InvalidActivityTimeException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduledTime {

    @Column(nullable = false)
    private LocalTime beginAt;

    @Column(nullable = false)
    private LocalTime endAt;

    private ScheduledTime(LocalTime beginAt, LocalTime endAt) {
        validateActTime(beginAt, endAt);
        this.beginAt = beginAt;
        this.endAt = endAt;
    }

    public static ScheduledTime of(LocalTime beginAt, LocalTime endAt) {
        return new ScheduledTime(beginAt, endAt);
    }

    private static void validateActTime(LocalTime beginAt, LocalTime endAt) {
        if (beginAt == null || endAt == null) {
            throw new InvalidActivityTimeException("시작 시간과 종료 시간은 필수 입력값입니다.");
        }
        if (beginAt.isAfter(endAt)) {
            throw new InvalidActivityTimeException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }
    }
}
