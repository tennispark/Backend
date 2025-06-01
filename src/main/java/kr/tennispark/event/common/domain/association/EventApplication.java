package kr.tennispark.event.common.domain.association;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.event.common.domain.Event;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE event_application SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class EventApplication extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    public static EventApplication of(Event event, Member member) {
        return new EventApplication(event, member);
    }
}
