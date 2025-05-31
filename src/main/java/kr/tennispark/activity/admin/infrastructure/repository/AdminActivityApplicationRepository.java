package kr.tennispark.activity.admin.infrastructure.repository;

import java.util.Optional;
import kr.tennispark.activity.admin.application.exception.NoSuchActivityApplicationException;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    Page<ActivityApplication> findAllByActivityOrderByCreatedAtDesc(Activity activity, Pageable pageable);

    Optional<ActivityApplication> findByMember_IdAndActivity_Id(Long memberId, Long activityId);

    default ActivityApplication getByMemberIdAndActivityId(Long memberId, Long activityId) {
        return findByMember_IdAndActivity_Id(memberId, activityId)
                .orElseThrow(NoSuchActivityApplicationException::new);
    }
}
