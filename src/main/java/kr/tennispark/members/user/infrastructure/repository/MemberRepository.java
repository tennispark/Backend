package kr.tennispark.members.user.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;
import kr.tennispark.members.common.domain.exception.NoSuchMemberException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhone_Number(String number);

    List<Member> findByNameContaining(String name);

    default Member getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchMemberException::new);
    }

    Optional<Member> findByPhone_Number(String number);

    int countByMemberShipType(MemberShipType memberShipType);

    @Query("""
            SELECT m 
            FROM Member m 
            ORDER BY m.matchPoint DESC
            LIMIT 1
            """)
    Optional<Member> findTopScorerMember();

    @Query("""
            SELECT SUM(m.matchPoint)
            FROM Member m
            WHERE m.id = :memberId
            """)
    Integer sumScoreByMemberId(@Param("memberId") Long memberId);
}
