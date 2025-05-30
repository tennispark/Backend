package kr.tennispark.activity.admin.infrastructure.repository;

import kr.tennispark.activity.common.domain.ActivityInfo;
import kr.tennispark.activity.common.domain.exception.NoSuchActivityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityInfoRepository extends JpaRepository<ActivityInfo, Long> {

    Page<ActivityInfo> findAllByOrderByCreatedAtDesc(Pageable pageable);

    default ActivityInfo getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchActivityException::new);
    }
}
