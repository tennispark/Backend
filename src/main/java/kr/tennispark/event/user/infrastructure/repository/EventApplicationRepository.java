package kr.tennispark.event.user.infrastructure.repository;

import kr.tennispark.event.common.domain.Event;
import kr.tennispark.event.common.domain.association.EventApplication;
import kr.tennispark.members.common.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventApplicationRepository extends JpaRepository<EventApplication, Long> {

    @Query("""
                SELECT COUNT(ea) > 0
                FROM EventApplication ea
                WHERE ea.member = :member
                  AND ea.event = :event
                  AND DATE(ea.createdAt) = CURRENT_DATE
                  AND ea.status = true
            """)
    boolean existsByMemberToday(@Param("member") Member member, @Param("event") Event event);

    long count();
}
