package kr.tennispark.activity.user.application.service;

import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import kr.tennispark.activity.common.domain.exception.NoSuchActivityException;
import kr.tennispark.activity.user.application.exception.DuplicateApplicationException;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityApplicationRepository;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityRepository;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityCommandService {

    private final UserActivityRepository activityRepository;
    private final UserActivityApplicationRepository applicationRepository;

    @Transactional
    public void applyActivity(Member member, Long activityId) {
        Activity activity = loadAndLockActivity(activityId);

        preventDuplicate(member, activity);

        activity.incrementParticipant();

        recordApplication(member, activity);
    }

    private Activity loadAndLockActivity(Long activityId) {
        return activityRepository.findForUpdate(activityId)
                .orElseThrow(NoSuchActivityException::new);
    }

    private void preventDuplicate(Member member, Activity activity) {
        boolean already = applicationRepository.existsByMemberAndActivityAndApplicationStatus(
                member, activity, ApplicationStatus.APPROVED);
        if (already) {
            throw new DuplicateApplicationException();
        }
    }

    private void recordApplication(Member member, Activity activity) {
        ActivityApplication app =
                ActivityApplication.approve(member, activity);
        applicationRepository.save(app);
    }
}
