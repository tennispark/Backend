package kr.tennispark.event.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.event.domain.exception.InvalidEventException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE event SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class Event extends BaseEntity {

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50)
    private String detail;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private String imageUrl;

    public static Event of(String name, String detail, Integer point, String imageUrl) {
        validatePoint(point);
        return new Event(name, detail, point, imageUrl);
    }

    public static void validatePoint(Integer point) {
        if (point < 0) {
            throw new InvalidEventException("포인트는 0 이상이어야 합니다.");
        }
    }
}
