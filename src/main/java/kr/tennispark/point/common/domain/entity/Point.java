package kr.tennispark.point.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
@SQLDelete(sql = "UPDATE point SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class Point extends BaseEntity {

    @OneToOne(mappedBy = "point")
    private Member member;

    @Column(nullable = false)
    private Integer totalPoint;

    public static Point of(Member member) {
        return new Point(member, 0);
    }

    public void updatePoint(int amount) {
        this.totalPoint += amount;
    }
}
