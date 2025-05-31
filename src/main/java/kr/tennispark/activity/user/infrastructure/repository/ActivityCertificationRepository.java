package kr.tennispark.activity.user.infrastructure.repository;

import kr.tennispark.activity.common.domain.ActivityCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityCertificationRepository
        extends JpaRepository<ActivityCertification, Long> {
}
