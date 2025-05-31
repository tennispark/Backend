package kr.tennispark.event.admin.infrastructure.repository;

import kr.tennispark.event.common.domain.Event;
import kr.tennispark.event.common.domain.exception.NoSuchEventException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Long countByStatus(boolean status);

    default Event getById(Long eventId) {
        return findById(eventId)
                .orElseThrow(NoSuchEventException::new);
    }
}
