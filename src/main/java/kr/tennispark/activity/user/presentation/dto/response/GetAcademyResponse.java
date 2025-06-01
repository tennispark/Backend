package kr.tennispark.activity.user.presentation.dto.response;

import java.util.List;
import kr.tennispark.activity.common.domain.Activity;

public record GetAcademyResponse(List<AcademyDTO> activities) {

    public static GetAcademyResponse of(List<Activity> activities) {
        List<AcademyDTO> dtos = activities.stream()
                .map(AcademyDTO::of)
                .toList();
        return new GetAcademyResponse(dtos);
    }
}

