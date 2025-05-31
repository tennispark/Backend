package kr.tennispark.event.user.infrastructure.repository;

import kr.tennispark.event.common.domain.Event;
import kr.tennispark.event.common.domain.association.EventApplication;
import kr.tennispark.members.common.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventApplicationRepository extends JpaRepository<EventApplication, Long> {

    boolean existsByEventAndMember(Event event, Member member);
}
