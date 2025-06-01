package kr.tennispark.event.user.infrastructure.repository;

import java.time.LocalDateTime;
import kr.tennispark.event.common.domain.Event;
import kr.tennispark.event.common.domain.association.EventApplication;
import kr.tennispark.members.common.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventApplicationRepository extends JpaRepository<EventApplication, Long> {

    boolean existsByEventAndMember(Event event, Member member);

    @Query("""
                    SELECT COUNT(DISTINCT ea.member.id) 
                    FROM EventApplication ea
                    WHERE ea.createdAt BETWEEN :start AND :end
            """)
    int countDistinctMemberIdByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    long count();
}
