package kr.tennispark.activity.user.application.service;

import java.time.LocalDateTime;
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

    private static final int ONE_WEEK = 1;
    private final ActivityImageRepository activityImageRepository;

    public GetActivityImageResponse getActivityImage() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(ONE_WEEK);

        return GetActivityImageResponse.of(activityImageRepository.findAllByCreatedAtBetween(oneWeekAgo, now)
                .stream().map(ActivityImage::getImageUrl)
                .toList());
    }
}
