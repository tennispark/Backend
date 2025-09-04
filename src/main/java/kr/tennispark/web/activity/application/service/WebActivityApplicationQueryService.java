package kr.tennispark.web.activity.application.service;

import java.time.LocalDate;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ActivityType;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import kr.tennispark.activity.common.domain.vo.WeekPeriod;
import kr.tennispark.web.activity.infrastructure.repository.WebAdminActivityApplicationRepository;
import kr.tennispark.web.activity.infrastructure.repository.WebAdminActivityRepository;
import kr.tennispark.web.activity.presentation.dto.ActivityApplicationRowDTO;
import kr.tennispark.web.activity.presentation.dto.GetWeeklyActivityApplicationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WebActivityApplicationQueryService {

    private final WebAdminActivityRepository activityRepository;
    private final WebAdminActivityApplicationRepository applicationRepository;

    public GetWeeklyActivityApplicationsResponse getThisWeeksApplication(ActivityType type) {
        LocalDate fromDate = WeekPeriod.thisWeek().start();

        List<Activity> activities = activityRepository.findFromDate(fromDate, type);

        List<ActivityApplicationRowDTO> rows = activities.stream()
                .map(a -> {
                    List<ActivityApplication> approved = applicationRepository.findAllByActivityAndStatus(
                            a, ApplicationStatus.APPROVED
                    );
                    return ActivityApplicationRowDTO.of(a, approved);
                })
                .toList();

        return new GetWeeklyActivityApplicationsResponse(rows);
    }
}
