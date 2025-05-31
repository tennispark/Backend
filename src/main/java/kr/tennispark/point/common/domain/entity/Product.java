package kr.tennispark.point.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import kr.tennispark.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE Product SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("status = true")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer quantity;
}
