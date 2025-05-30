package kr.tennispark.activity.admin.application.impl;

import kr.tennispark.activity.admin.application.ActivityAdminUseCase;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseDTO;
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

    private final ActivityRepository activityRepository;

    @Override
    public void registerActivityInfo(ManageActivityRequestDTO request) {
        ActivityInfo act = ActivityInfo.of(request.courtName(),
                request.placeName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount());

        activityRepository.save(act);
    }

    @Override
    public void modifyActivityInfoDetails(Long activityId, ManageActivityRequestDTO request) {
        ActivityInfo activityInfo = activityRepository.getById(activityId);

        activityInfo.modifyActivityInfoDetails(request.courtName(),
                request.placeName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount());
    }

    @Override
    public GetActivityResponseDTO getActivityInfoList(Integer page, Integer size) {
        Page<ActivityInfo> actPage = activityRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        return GetActivityResponseDTO.of(actPage);
    }
}
