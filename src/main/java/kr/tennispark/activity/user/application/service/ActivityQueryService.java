package kr.tennispark.activity.user.application.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.enums.ActivityType;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityRepository;
import kr.tennispark.activity.user.presentation.dto.response.GetAcademyResponse;
import kr.tennispark.activity.user.presentation.dto.response.GetActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityQueryService {
    private static final String ZONE = "Asia/Seoul";

    private final UserActivityRepository activityRepository;

    public GetActivityResponse getAllAvailableActivitiesFromToday() {
        LocalDate today = LocalDate.now(ZoneId.of(ZONE));
        List<Activity> activities = activityRepository.findAllGeneralActivitiesFrom(today, ActivityType.GENERAL);
        return GetActivityResponse.of(activities);
    }


    public GetAcademyResponse getAllAvailableAcademiesFromToday() {
        LocalDate today = LocalDate.now(ZoneId.of(ZONE));
        List<Activity> activities = activityRepository.findAllGeneralActivitiesFrom(today, ActivityType.ACADEMY);
        return GetAcademyResponse.of(activities);
    }
}