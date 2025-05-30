package kr.tennispark.activity.user.application.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityRepository;
import kr.tennispark.activity.user.presentation.dto.response.GetActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityQueryService {

    private final UserActivityRepository activityRepository;

    public GetActivityResponse getAllAvailableActivitiesFromToday() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        List<Activity> activities = activityRepository.findAllByDateGreaterThanEqual(today);
        return GetActivityResponse.of(activities);
    }
}