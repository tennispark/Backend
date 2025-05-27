package kr.tennispark.activity.admin.infrastructure.repository;

import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.exception.NoSuchActivityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Page<Activity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    default Activity getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchActivityException::new);
    }
}
