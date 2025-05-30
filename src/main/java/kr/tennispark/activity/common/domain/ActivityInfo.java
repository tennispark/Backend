package kr.tennispark.activity.common.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.time.LocalTime;
import java.util.List;
import kr.tennispark.activity.common.domain.enums.CourtType;
import kr.tennispark.activity.common.domain.enums.Days;
import kr.tennispark.activity.common.domain.vo.Place;
import kr.tennispark.activity.common.domain.vo.ScheduledTime;
import kr.tennispark.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE activity_info SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class ActivityInfo extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourtType courtType;

    @Embedded
    private Place place;

    @Embedded
    private ScheduledTime time;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "active_days",
            joinColumns = @JoinColumn(name = "info_id"))
    @Column(name = "active_day")
    @Enumerated(EnumType.STRING)
    private List<Days> activeDays;

    @Column(nullable = false)
    private Boolean isRecurring;

    @Column(nullable = false)
    private Integer capacity;

    public static ActivityInfo of(CourtType courtType, String placeName, String address, LocalTime beginAt,
                                  LocalTime endAt,
                                  List<String> activeDays, Boolean isRecurring, Integer capacity) {
        return new ActivityInfo(courtType,
                Place.of(placeName, address),
                ScheduledTime.of(beginAt, endAt),
                Days.from(activeDays),
                isRecurring,
                capacity);
    }

    public void modifyActivityInfoDetails(
            CourtType courtType, String placeName, String address, LocalTime beginAt, LocalTime endAt,
            List<String> activeDays, Boolean isRecurring, Integer capacity) {
        this.courtType = courtType;
        this.place = Place.of(placeName, address);
        this.time = ScheduledTime.of(beginAt, endAt);
        this.activeDays = Days.from(activeDays);
        this.isRecurring = isRecurring;
        this.capacity = capacity;
    }
}
