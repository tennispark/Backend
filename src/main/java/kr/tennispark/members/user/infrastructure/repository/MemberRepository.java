package kr.tennispark.members.user.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;
import kr.tennispark.members.common.domain.entity.vo.Phone;
import kr.tennispark.members.common.domain.exception.NoSuchMemberException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /* ✅ 핵심 수정: VO 기준 조회 */
    boolean existsByPhone(Phone phone);
    Optional<Member> findByPhone(Phone phone);

    /* ❗ 레거시 (사용 금지, 삭제는 선택) */
    // boolean existsByPhone_Number(String number);
    // Optional<Member> findByPhone_Number(String number);

    List<Member> findByNameContaining(String name);

    default Member getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchMemberException::new);
    }

    int countByMemberShipType(MemberShipType memberShipType);

    @Query("""
            SELECT m 
            FROM Member m 
            WHERE m.status = true
            ORDER BY m.matchPoint DESC
            """)
    Optional<Member> findTopScorerMember();

    @Query("""
            SELECT SUM(m.matchPoint)
            FROM Member m
            WHERE m.id = :memberId
              AND m.status = true
            """)
    Integer sumScoreByMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query("UPDATE Member m SET m.tennisCareer = m.tennisCareer + 1")
    void bulkIncreaseTennisCareer();

    List<Member> findByIdIn(List<Long> memberIds);

    @Query("""
            SELECT m
            FROM Member m
            WHERE m.status = true
              AND m.fcmToken IS NOT NULL
              AND TRIM(m.fcmToken) <> ''
            ORDER BY m.id ASC
            """)
    List<Member> findAllWithValidFcmToken();
}
