package kr.tennispark.web.activity.presentation.dto;

import java.util.List;

public record GetWeeklyActivityApplicationsResponse(
        List<ActivityApplicationRowDTO> rows
) {
}
