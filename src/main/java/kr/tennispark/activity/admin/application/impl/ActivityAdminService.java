package kr.tennispark.activity.admin.application.impl;

import java.time.LocalDate;
import java.util.List;
import kr.tennispark.activity.admin.application.ActivityAdminUseCase;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityInfoRepository;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.admin.infrastructure.repository.AdminActivityApplicationRepository;
import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityApplicationRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityInfoRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityApplicantResponseDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityApplicationResponseDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseInfoDTO;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.ActivityInfo;
import kr.tennispark.activity.common.domain.vo.WeekPeriod;
import kr.tennispark.notification.application.ActivityNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityAdminService implements ActivityAdminUseCase {

    private static final Integer ONE_WEEK = 1;

    private final ActivityNotificationService activityNotificationService;

    private final ActivityRepository activityRepository;
    private final ActivityInfoRepository activityInfoRepository;
    private final AdminActivityApplicationRepository activityApplicationRepository;


    @Override
    @Transactional
    public void registerActivityInfo(ManageActivityInfoRequestDTO request) {
        ActivityInfo act = ActivityInfo.of(request.courtType(),
                request.placeName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount(),
                request.courtName());

        act = activityInfoRepository.save(act);
        generateInitialActivities(act);
    }

    private void generateInitialActivities(ActivityInfo template) {
        LocalDate today = WeekPeriod.today();
        WeekPeriod week = WeekPeriod.current();

        List<Activity> activities = template.getActiveDays().stream()
                .map(day -> Activity.of(template, week.toDate(day)))
                .filter(act -> !act.getDate().isBefore(today))
                .toList();

        activityRepository.saveAll(activities);
    }

    @Override
    @Transactional
    public void modifyActivityInfoDetails(Long activityInfoId, ManageActivityInfoRequestDTO request) {
        ActivityInfo activityInfo = activityInfoRepository.getById(activityInfoId);

        activityInfo.modifyActivityInfoDetails(request.courtType(),
                request.placeName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount(),
                request.courtName());
    }

    @Override
    @Transactional
    public void modifyActivityApplication(Long applicantId, Long activityId,
                                          ManageActivityApplicationRequestDTO request) {
        ActivityApplication activityApplication = activityApplicationRepository.getByMemberIdAndActivityId(
                applicantId, activityId);

        activityApplication.changeStatus(request.applicationStatus());

        if (activityApplication.getApplicationStatus().isAccepted()) {
            activityNotificationService.notifyApprovedApplication(activityApplication);
        }
    }


    @Override
    @Transactional
    public void deleteActivityInfo(Long activityInfoId) {
        ActivityInfo activityInfo = activityInfoRepository.getById(activityInfoId);
        List<Activity> activities = activityRepository.findAllByTemplateAndDateAfter(activityInfo, LocalDate.now());

        for (Activity activity : activities) {
            activityApplicationRepository.deleteAllByActivity(activity);
        }

        activityRepository.deleteAll(activities);
        activityInfoRepository.delete(activityInfo);
    }

    @Override
    public GetActivityResponseInfoDTO getActivityInfoList(Integer page, Integer size) {
        Page<ActivityInfo> actPage = activityInfoRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        return GetActivityResponseInfoDTO.of(actPage);
    }

    @Override
    public GetActivityApplicationResponseDTO getActivityApplicationList(Integer page, Integer size) {
        Page<Activity> activityPage = activityRepository.findRecentTwoWeeks
                (PageRequest.of(page, size), LocalDate.now().minusWeeks(ONE_WEEK));

        return GetActivityApplicationResponseDTO.of(activityPage);
    }

    @Override
    public GetActivityApplicantResponseDTO getActivityApplicantList(Long activityId, Integer page, Integer size) {
        Activity activity = activityRepository.getById(activityId);

        Page<ActivityApplication> applicantPage = activityApplicationRepository.findAllValidByActivity(
                activity, PageRequest.of(page, size));

        return GetActivityApplicantResponseDTO.of(applicantPage);
    }
}
