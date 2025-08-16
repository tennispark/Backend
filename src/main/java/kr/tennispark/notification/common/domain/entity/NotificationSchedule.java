package kr.tennispark.notification.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.notification.common.domain.entity.enums.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE notification_schedule SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class NotificationSchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Activity activity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private LocalDateTime scheduledTime;

    @Column(nullable = false)
    private String targetToken;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public static NotificationSchedule of(Activity activity, NotificationType type, LocalDateTime scheduledTime,
                                          String targetToken, Member member) {
        return new NotificationSchedule(activity, type, scheduledTime, targetToken, member);
    }

}
