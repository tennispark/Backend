package kr.tennispark.event.infrastructure.repository;

import kr.tennispark.common.exception.base.NotFoundException;
import kr.tennispark.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    default Event getById(Long eventId) {
        return findById(eventId)
                .orElseThrow(NotFoundException::new);
    }
}
