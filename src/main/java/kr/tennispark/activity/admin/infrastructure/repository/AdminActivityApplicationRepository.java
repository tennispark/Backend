package kr.tennispark.activity.admin.infrastructure.repository;

import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    Page<ActivityApplication> findAllByActivityOrderByCreatedAtDesc(Activity activity, Pageable pageable);
}
