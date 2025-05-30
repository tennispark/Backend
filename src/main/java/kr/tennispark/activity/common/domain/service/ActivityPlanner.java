package kr.tennispark.activity.common.domain.service;

import java.util.List;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityInfo;
import kr.tennispark.activity.common.domain.vo.WeekPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityPlanner {

    private final ActivityRepository activityRepo;

    public List<Activity> plan(ActivityInfo info, WeekPeriod week) {
        return info.getActTime().getActiveDays().stream()
                .map(week::toDate)
                .filter(week::contains)
                .filter(date ->
                        !activityRepo.existsByTemplateAndDate(info, date))
                .map(date -> Activity.of(info, date))
                .toList();
    }
}

