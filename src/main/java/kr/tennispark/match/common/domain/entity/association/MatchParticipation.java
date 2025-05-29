package kr.tennispark.match.common.domain.entity.association;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.match.common.domain.entity.MatchResult;
import kr.tennispark.match.common.domain.entity.exception.InvalidMatchResultException;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE member_participation SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class MatchParticipation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_result_id", nullable = false)
    private MatchResult matchResult;

    @Column(nullable = false)
    private Boolean isWinner;

    @Column(nullable = false)
    private Integer score;

    public static MatchParticipation of(Member member, MatchResult matchResult, Boolean isWinner, Integer score) {
        validateScore(score);
        return new MatchParticipation(member, matchResult, isWinner, score);
    }

    private static void validateScore(Integer score) {
        if (score < 0) {
            throw new InvalidMatchResultException("점수는 0 이상이어야 합니다.");
        }
    }
}
