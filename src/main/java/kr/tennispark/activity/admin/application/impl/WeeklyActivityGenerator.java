package kr.tennispark.activity.admin.application.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityInfoRepository;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityInfo;
import kr.tennispark.activity.common.domain.service.ActivityPlanner;
import kr.tennispark.activity.common.domain.vo.WeekPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeeklyActivityGenerator {

    private final ActivityInfoRepository activityInfoRepository;
    private final ActivityPlanner activityPlanner;
    private final ActivityRepository activityRepository;

    @Transactional
    public void generateCurrentWeek() {
        WeekPeriod week = WeekPeriod.current();

        List<ActivityInfo> templates = activityInfoRepository.findAllByIsRecurringTrue();

        List<Activity> newActivities = templates.stream()
                .flatMap(t -> activityPlanner.plan(t, week).stream())
                .toList();

        activityRepository.saveAll(newActivities);
    }
}
