package kr.tennispark.act.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.time.LocalTime;
import java.util.List;
import kr.tennispark.act.domain.vo.ScheduledTime;
import kr.tennispark.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE act SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class Act extends BaseEntity {

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

    public static Act of(String courtName, String address, LocalTime beginAt, LocalTime endAt,
                         List<String> activeDays, Boolean isRecurring, Integer participantCount) {
        return new Act(courtName, address, ScheduledTime.of(beginAt, endAt, activeDays), isRecurring,
                participantCount);
    }

    public void modifyActDetails(
            String courtName, String address, LocalTime beginAt, LocalTime endAt,
            List<String> activeDays, Boolean isRecurring, Integer participantCount) {
        this.courtName = courtName;
        this.address = address;
        this.actTime = ScheduledTime.of(beginAt, endAt, activeDays);
        this.isRecurring = isRecurring;
        this.participantCount = participantCount;
    }
}
