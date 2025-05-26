package kr.tennispark.activity.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.time.LocalTime;
import java.util.List;
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
@SQLDelete(sql = "UPDATE activity SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class Activity extends BaseEntity {

    @Column(nullable = false)
    private String courtName;

    @Column(nullable = false)
    private String address;

    @Embedded
    private ScheduledTime actTime;

    @Column(nullable = false)
    private Boolean isRecurring;

    @Column(nullable = false)
    private Integer participantCount;

    public static Activity of(String courtName, String address, LocalTime beginAt, LocalTime endAt,
                         List<String> activeDays, Boolean isRecurring, Integer participantCount) {
        return new Activity(courtName, address, ScheduledTime.of(beginAt, endAt, activeDays), isRecurring,
                participantCount);
    }

    public void modifyActivityDetails(
            String courtName, String address, LocalTime beginAt, LocalTime endAt,
            List<String> activeDays, Boolean isRecurring, Integer participantCount) {
        this.courtName = courtName;
        this.address = address;
        this.actTime = ScheduledTime.of(beginAt, endAt, activeDays);
        this.isRecurring = isRecurring;
        this.participantCount = participantCount;
    }
}
