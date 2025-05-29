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
    private String title;

    @Column(nullable = false, length = 50)
    private String content;

    @Column(nullable = false)
    private Integer point;

    @Column
    private String imageUrl;

    public static Event of(String title, String detail, Integer point) {
        validatePoint(point);
        return new Event(title, detail, point, null);
    }

    private static void validatePoint(Integer point) {
        if (point < 0) {
            throw new InvalidEventException("포인트는 0 이상이어야 합니다.");
        }
    }

    public void modifyEventDetails(
            String title,
            String detail,
            Integer point
    ) {
        validatePoint(point);
        this.title = title;
        this.content = detail;
        this.point = point;
    }

    public void attachQrImageUrl(String qrImageUrl) {
        this.imageUrl = qrImageUrl;
    }

}
