package kr.tennispark.membership.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.membership.common.domain.entity.enums.ActivityDuration;
import kr.tennispark.membership.common.domain.entity.enums.CourtType;
import kr.tennispark.membership.common.domain.entity.enums.MembershipStatus;
import kr.tennispark.membership.common.domain.entity.enums.MembershipType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE membership SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Membership extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    private String recommender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'APPROVED'")
    private MembershipStatus membershipStatus = MembershipStatus.APPROVED;

    @Column(length = 100, nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_type", nullable = false, length = 10)
    private MembershipType membershipType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourtType courtType;

    @Enumerated(EnumType.STRING)
    @Column(name = "period", nullable = false, length = 10)
    private ActivityDuration period;

    private Membership(Member member, String recommender, String reason, MembershipType membershipType, CourtType court,
                      ActivityDuration period) {
        this.member = member;
        this.recommender = recommender;
        this.reason = reason;
        this.membershipType = membershipType;
        this.courtType = court;
        this.period = period;
    }

    public static Membership of(
            Member member, String recommender, String reason, MembershipType membershipType, CourtType court,
            ActivityDuration period){
       return new Membership(member, recommender, reason, membershipType, court,period);
    }
}
