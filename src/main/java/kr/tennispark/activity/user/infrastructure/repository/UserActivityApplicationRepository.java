package kr.tennispark.activity.user.infrastructure.repository;

import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import kr.tennispark.members.common.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    boolean existsByMemberAndActivityAndApplicationStatus(
            Member member, Activity activity, ApplicationStatus status);
}
