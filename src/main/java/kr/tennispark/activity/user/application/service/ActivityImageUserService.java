package kr.tennispark.activity.user.application.service;

import kr.tennispark.activity.admin.infrastructure.repository.ActivityImageRepository;
import kr.tennispark.activity.common.domain.ActivityImage;
import kr.tennispark.activity.user.presentation.dto.response.GetActivityImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityImageUserService {

    private final ActivityImageRepository activityImageRepository;

    public GetActivityImageResponse getActivityImage() {
        return GetActivityImageResponse.of(activityImageRepository.findAll()
                .stream().map(ActivityImage::getImageUrl)
                .toList());
    }
}
