package kr.tennispark.activity.admin.application.impl;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActivityAdminService implements ActivityAdminUseCase {

    private final AdminActivityApplicationRepository activityApplicationRepository;
    private final ActivityInfoRepository activityInfoRepository;
    private final ActivityRepository activityRepository;

    @Override
    public void registerActivityInfo(ManageActivityInfoRequestDTO request) {
        ActivityInfo act = ActivityInfo.of(request.courtType(),
                request.placeName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount());

        activityInfoRepository.save(act);
    }

    @Override
    public void modifyActivityInfoDetails(Long activityId, ManageActivityInfoRequestDTO request) {
        ActivityInfo activityInfo = activityInfoRepository.getById(activityId);

        activityInfo.modifyActivityInfoDetails(request.courtType(),
                request.placeName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount());
    }

    @Override
    public void modifyActivityApplication(Long applicantId, Long activityId,
                                          ManageActivityApplicationRequestDTO request) {
        ActivityApplication activityApplication = activityApplicationRepository.getByMemberIdAndActivityId(
                applicantId, activityId);

        activityApplication.modifyStatus(request.applicationStatus());
    }

    @Override
    public GetActivityResponseInfoDTO getActivityInfoList(Integer page, Integer size) {
        Page<ActivityInfo> actPage = activityInfoRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        return GetActivityResponseInfoDTO.of(actPage);
    }

    @Override
    public GetActivityApplicationResponseDTO getActivityApplicationList(Integer page, Integer size) {
        Page<Activity> activityPage = activityRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        return GetActivityApplicationResponseDTO.of(activityPage);
    }

    @Override
    public GetActivityApplicantResponseDTO getActivityApplicantList(Long activityId, Integer page, Integer size) {
        Activity activity = activityRepository.getById(activityId);

        Page<ActivityApplication> applicantPage = activityApplicationRepository.findAllByActivityOrderByCreatedAtDesc(
                activity, PageRequest.of(page, size));

        return GetActivityApplicantResponseDTO.of(applicantPage);
    }
}
