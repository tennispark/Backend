package kr.tennispark.activity.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import kr.tennispark.activity.common.domain.enums.ActivityName;
import kr.tennispark.activity.common.domain.enums.ActivityType;
import kr.tennispark.activity.common.domain.exception.CapacityExceededException;
import kr.tennispark.activity.common.domain.exception.ParticipantUnderflowException;
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
@SQLDelete(sql = "UPDATE activity SET status=false WHERE id=?")
@SQLRestriction("status=true")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Activity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "info_id")
    private ActivityInfo template;

    @Column(nullable = false)
    private LocalDate date;

    @Embedded
    private ScheduledTime scheduledTime;

    @Column(nullable = false)
    private Integer participantCount = 0;

    @Column(nullable = false)
    private Integer capacity;

    @Embedded
    private Place place;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'GENERAL'")
    private ActivityType type = ActivityType.GENERAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityName activityName;

    @Column(nullable = false)
    private String courtName;

    public static Activity of(ActivityInfo template, LocalDate date) {
        return new Activity(
                template,
                date,
                ScheduledTime.of(
                        template.getTime().getBeginAt(),
                        template.getTime().getEndAt()
                ),
                0,
                template.getCapacity(),
                template.getPlace(),
                template.getType(),
                template.getActivityName(),
                template.getCourtName()
        );
    }

    public void incrementParticipant() {
        if (participantCount >= capacity) {
            throw new CapacityExceededException();
        }
        this.participantCount++;
    }

    public void decrementParticipant() {
        if (participantCount <= 0) {
            throw new ParticipantUnderflowException();
        }
        this.participantCount--;
    }
}

