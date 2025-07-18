package kr.tennispark.activity.user.application.service;

import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ActivityType;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import kr.tennispark.activity.common.domain.exception.NoSuchActivityException;
import kr.tennispark.activity.user.application.exception.DuplicateApplicationException;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityApplicationRepository;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityRepository;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        apply(member, activityId, ActivityType.GENERAL);
    }

    @Transactional
    public void applyAcademy(Member member, Long activityId) {
        apply(member, activityId, ActivityType.ACADEMY);
    }

    private void apply(Member member, Long activityId, ActivityType type) {
        Activity activity = loadAndLockActivity(activityId, type);
        activity.incrementApplicant();
        preventDuplicate(member, activity);
        try {
            recordApplication(member, activity);
        } catch (DataIntegrityViolationException e) {
            if (isDuplicateApplicationKeyViolation(e)) {
                throw new DuplicateApplicationException();
            }
            throw e;
        }
    }

    private boolean isDuplicateApplicationKeyViolation(DataIntegrityViolationException e) {
        return e.getCause() != null &&
                e.getCause().getMessage() != null &&
                e.getCause().getMessage().contains("UK_member_activity");
    }

    private Activity loadAndLockActivity(Long activityId, ActivityType type) {
        return activityRepository.findForUpdate(activityId, type)
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
                ActivityApplication.of(member, activity);
        applicationRepository.save(app);
    }
}
