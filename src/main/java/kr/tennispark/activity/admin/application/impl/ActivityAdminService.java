package kr.tennispark.activity.admin.application.impl;

import kr.tennispark.activity.admin.application.ActivityAdminUseCase;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseDTO;
import kr.tennispark.activity.common.domain.Activity;
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
    public void registerActivity(ManageActivityRequestDTO request) {
        Activity act = Activity.of(request.courtName(),
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
    public void modifyActivityDetails(Long activityId, ManageActivityRequestDTO request) {
        Activity activity = activityRepository.getById(activityId);

        activity.modifyActivityDetails(request.courtName(),
                request.placeName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount());
    }

    @Override
    public GetActivityResponseDTO getActivityList(Integer page, Integer size) {
        Page<Activity> actPage = activityRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        return GetActivityResponseDTO.of(actPage);
    }
}
