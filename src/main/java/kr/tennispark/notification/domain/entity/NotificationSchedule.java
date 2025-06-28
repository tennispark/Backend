package kr.tennispark.notification.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.notification.domain.entity.enums.NotificationType;
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
    private boolean isSent = false;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "notification_target_tokens", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "token", nullable = false)
    private List<String> targetTokens;

    public static NotificationSchedule of(Activity activity, NotificationType type, LocalDateTime scheduledTime, List<String> targetTokens) {
        return new NotificationSchedule(activity, type, scheduledTime, false, targetTokens);
    }

    public void markAsSent() {
        this.isSent = true;
    }
}