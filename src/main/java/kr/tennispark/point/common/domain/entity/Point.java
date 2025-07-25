package kr.tennispark.point.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.common.domain.exception.NotEnoughPointException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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
    @ColumnDefault("0")
    private Integer totalPoint = 0;

    public static Point of(Member member) {
        return new Point(member, 0);
    }

    public void updatePoint(int amount) {
        if (totalPoint + amount < 0) {
            throw new NotEnoughPointException();
        }
        this.totalPoint += amount;
    }
}
