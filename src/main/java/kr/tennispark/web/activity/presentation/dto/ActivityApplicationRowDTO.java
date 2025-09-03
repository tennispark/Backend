package kr.tennispark.web.activity.presentation.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;

public record ActivityApplicationRowDTO(
        Long activityId,
        String date,
        String placeName,
        String courtName,
        int applicantCount,
        int capacity,
        List<String> participants
) {
    private static final DateTimeFormatter MONTH_DAY = DateTimeFormatter.ofPattern("M/d");

    public static ActivityApplicationRowDTO of(Activity a, List<String> approvedNames) {
        String place = a.getPlace().getName();

        return new ActivityApplicationRowDTO(
                a.getId(),
                a.getDate().format(MONTH_DAY),
                place,
                a.getCourtName(),
                a.getApplicantCount(),
                a.getCapacity(),
                approvedNames
        );
    }
}
