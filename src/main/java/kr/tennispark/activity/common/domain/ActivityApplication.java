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
import kr.tennispark.activity.admin.application.exception.AlreadyApprovedApplicationException;
import kr.tennispark.activity.admin.application.exception.AlreadyCanceledApplicationException;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static ActivityApplication approve(Member m, Activity a) {
        return new ActivityApplication(m, a, ApplicationStatus.APPROVED);
    }

    public void cancel() {
        if (this.applicationStatus == ApplicationStatus.CANCELED) {
            throw new AlreadyCanceledApplicationException();
        }
        this.applicationStatus = ApplicationStatus.CANCELED;
    }

    public void approve() {
        if (this.applicationStatus == ApplicationStatus.APPROVED) {
            throw new AlreadyApprovedApplicationException();
        }
        this.applicationStatus = ApplicationStatus.APPROVED;
    }

    public void modifyStatus(ApplicationStatus status) {
        if (status == ApplicationStatus.CANCELED) {
            cancel();
            activity.decrementParticipant();
        } else {
            approve();
            activity.incrementParticipant();
        }
    }
}

