package kr.tennispark.act.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import java.util.List;
import kr.tennispark.act.domain.enums.Days;
import kr.tennispark.act.domain.vo.ScheduledTime;
import kr.tennispark.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE act SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("status = true")
public class Act extends BaseEntity {

    private String courtName;

    private String address;

    @Embedded
    private ScheduledTime actTime;

    @ElementCollection(targetClass = Days.class)
    @CollectionTable(
            name = "active_days",
            joinColumns = @JoinColumn(name = "act_id")
    )
    @Column(name = "active_day")
    @Enumerated(EnumType.STRING)
    private List<Days> activeDays;

    private Boolean isRecurring;

    private Integer participantCount;
}
