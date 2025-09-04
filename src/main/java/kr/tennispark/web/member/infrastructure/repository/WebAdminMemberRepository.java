package kr.tennispark.web.member.infrastructure.repository;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WebAdminMemberRepository extends JpaRepository<Member, Long> {

    @Query("""
                SELECT m
                FROM Member m
                WHERE m.status = true
                ORDER BY m.name ASC, m.createdAt ASC, m.id ASC
            """)
    List<Member> findAllActiveMember();
}
