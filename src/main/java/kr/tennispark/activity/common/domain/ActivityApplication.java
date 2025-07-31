package kr.tennispark.activity.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
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
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "UK_member_activity",
                columnNames = {"member_id", "activity_id"}
        )
)
@SQLDelete(sql = "UPDATE activity_application SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class ActivityApplication extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    public static ActivityApplication of(Member m, Activity a) {
        return new ActivityApplication(m, a, ApplicationStatus.PENDING);
    }

    private static int participantChange(ApplicationStatus from, ApplicationStatus to) {
        boolean wasCounted = from.isCounted();
        boolean willCount = to.isCounted();
        if (wasCounted && !willCount) {
            return -1;
        }
        if (!wasCounted && willCount) {
            return +1;
        }
        return 0;
    }

    public void changeStatus(ApplicationStatus nextStatus) {
        if (this.applicationStatus == nextStatus) {
            return;
        }

        int change = participantChange(this.applicationStatus, nextStatus);
        if (change > 0) {
            activity.incrementParticipant();
        } else if (change < 0) {
            activity.decrementParticipant();
        }
        this.applicationStatus = nextStatus;
    }

    public void cancelByWithdrawal() {
        if (!this.applicationStatus.isCounted()) {
            delete();
            return;
        }

        activity.decrementParticipant();
        this.applicationStatus = ApplicationStatus.CANCELED;
        delete();
    }
}

