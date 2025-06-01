package kr.tennispark.members.common.domain.entity.association;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.PointReason;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE point_history SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class PointHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id", nullable = false)
    private Point point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PointReason reason;

    public static PointHistory of(Point point, Member member, int amount, PointReason reason) {
        return new PointHistory(point, member, amount, reason);
    }
}
