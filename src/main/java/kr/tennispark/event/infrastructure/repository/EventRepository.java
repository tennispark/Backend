package kr.tennispark.event.infrastructure.repository;

import kr.tennispark.event.domain.Event;
import kr.tennispark.event.domain.exception.NoSuchEventException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);

    default Event getById(Long eventId) {
        return findById(eventId)
                .orElseThrow(NoSuchEventException::new);
    }
}
