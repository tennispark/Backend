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
import kr.tennispark.activity.common.domain.enums.ActivityName;
import kr.tennispark.activity.common.domain.enums.ActivityType;
import kr.tennispark.activity.common.domain.enums.Days;
import kr.tennispark.activity.common.domain.vo.Place;
import kr.tennispark.activity.common.domain.vo.ScheduledTime;
import kr.tennispark.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE activity_info SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class ActivityInfo extends BaseEntity {

    @Embedded
    private Place place;

    @Embedded
    private ScheduledTime time;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "active_days",
            joinColumns = @JoinColumn(name = "info_id"))
    @Column(name = "active_day")
    @Enumerated(EnumType.STRING)
    private List<Days> activeDays;

    @Column(nullable = false)
    private Boolean isRecurring;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'GENERAL'")
    private ActivityType type = ActivityType.GENERAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityName activityName;

    @Column(nullable = false)
    private String courtName;

    public static ActivityInfo of(ActivityName activityName, String placeName, String address,
                                  LocalTime beginAt, LocalTime endAt,
                                  List<String> activeDays, Boolean isRecurring,
                                  Integer capacity, String courtName) {
        activityName.validateWith(ActivityType.GENERAL);
        return new ActivityInfo(
                Place.of(placeName, address),
                ScheduledTime.of(beginAt, endAt),
                Days.from(activeDays),
                isRecurring,
                capacity,
                ActivityType.GENERAL,
                activityName,
                courtName);
    }

    public void modifyActivityInfoDetails(
            ActivityName activityName, String placeName, String address, LocalTime beginAt, LocalTime endAt,
            List<String> activeDays, Boolean isRecurring, Integer capacity, String courtName) {
        activityName.validateWith(type);
        this.place = Place.of(placeName, address);
        this.time = ScheduledTime.of(beginAt, endAt);
        this.activeDays = Days.from(activeDays);
        this.isRecurring = isRecurring;
        this.capacity = capacity;
        this.activityName = activityName;
        this.courtName = courtName;
    }
}
