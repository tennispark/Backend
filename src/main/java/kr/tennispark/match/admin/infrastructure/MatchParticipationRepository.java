package kr.tennispark.match.admin.infrastructure;

import java.util.Optional;
import kr.tennispark.match.common.domain.entity.association.MatchParticipation;
import kr.tennispark.members.common.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchParticipationRepository extends JpaRepository<MatchParticipation, Long> {

    @Query("SELECT mp.member FROM MatchParticipation mp WHERE mp.status = true GROUP BY mp.member ORDER BY SUM(mp.score) DESC ")
    Optional<Member> findTopScorerMember();

    @Query("SELECT COALESCE(SUM(mp.score), 0) FROM MatchParticipation mp WHERE mp.member.id = :memberId AND mp.status = true ")
    int sumScoreByMemberId(@Param("memberId") Long memberId);
}
