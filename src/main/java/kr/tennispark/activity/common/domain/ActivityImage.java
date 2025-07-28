package kr.tennispark.activity.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import kr.tennispark.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE activity_image SET status = false WHERE id = ?")
@SQLRestriction("status = true")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActivityImage extends BaseEntity {

    @Column(nullable = false)
    private String imageUrl;

    public static ActivityImage of(String imageUrl) {
        return new ActivityImage(imageUrl);
    }
}
