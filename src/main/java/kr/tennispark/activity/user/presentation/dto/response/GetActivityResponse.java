package kr.tennispark.activity.user.presentation.dto.response;

import java.util.List;
import kr.tennispark.activity.common.domain.Activity;

public record GetActivityResponse(List<ActivityDTO> activities) {

    public static GetActivityResponse of(List<Activity> activities) {
        List<ActivityDTO> dtos = activities.stream()
                .map(ActivityDTO::of)
                .toList();
        return new GetActivityResponse(dtos);
    }
}

