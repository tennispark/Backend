package kr.tennispark.act.common.domain.vo;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import java.time.LocalTime;
import java.util.List;
import kr.tennispark.act.common.domain.enums.Days;
import kr.tennispark.act.common.domain.exception.InvalidActTimeException;
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

    @ElementCollection(targetClass = Days.class)
    @CollectionTable(
            name = "active_days",
            joinColumns = @JoinColumn(name = "act_id")
    )
    @Column(name = "active_day")
    @Enumerated(EnumType.STRING)
    private List<Days> activeDays;


    public static ScheduledTime of(LocalTime beginAt, LocalTime endAt, List<String> activeDays) {
        validateActTime(beginAt, endAt);
        return new ScheduledTime(beginAt, endAt, Days.from(activeDays));
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
