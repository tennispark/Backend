package kr.tennispark.activity.admin.application.impl;

import kr.tennispark.activity.admin.application.ActivityAdminUseCase;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityInfoRepository;
import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityInfoRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseInfoDTO;
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

    private final ActivityInfoRepository activityInfoRepository;

    @Override
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
                request.participantCount(),
                request.courtName());
    }

    @Override
    public GetActivityResponseInfoDTO getActivityInfoList(Integer page, Integer size) {
        Page<ActivityInfo> actPage = activityInfoRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        return GetActivityResponseInfoDTO.of(actPage);
    }
}
