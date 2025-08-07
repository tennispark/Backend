package kr.tennispark.match.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.match.common.domain.entity.exception.InvalidMatchResultException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE match_result SET status = false WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLRestriction("status = true")
public class MatchResult extends BaseEntity {

    @Column(nullable = false, name = "team_a_score")
    private Integer myTeamScore;

    @Column(nullable = false, name = "team_b_score")
    private Integer otherTeamScore;

    @Column(nullable = false)
    private LocalDate matchDate;

    public static MatchResult of(Integer teamAScore, Integer teamBScore, LocalDate matchDate) {
        validateMatchScores(teamAScore, teamBScore);
        return new MatchResult(teamAScore, teamBScore, matchDate);
    }

    private static void validateMatchScores(Integer teamAScore, Integer teamBScore) {
        if (teamAScore < 0 || teamBScore < 0) {
            throw new InvalidMatchResultException("경기 점수는 0 이상이어야 합니다.");
        }
    }
}
