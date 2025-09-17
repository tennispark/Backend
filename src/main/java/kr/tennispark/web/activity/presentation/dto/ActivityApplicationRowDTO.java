package kr.tennispark.web.activity.presentation.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ActivityName;

public record ActivityApplicationRowDTO(
        Long activityId,
        String date,
        String placeName,
        ActivityName courtType,
        String courtName,
        int participantCount,
        int capacity,
        List<String> participants
) {
    private static final DateTimeFormatter MONTH_DAY = DateTimeFormatter.ofPattern("M/d");

    public static ActivityApplicationRowDTO of(Activity a, List<ActivityApplication> applications) {
        List<String> names = applications.stream()
                .map(aa -> aa.getMember().getName())
                .toList();

        return new ActivityApplicationRowDTO(
                a.getId(),
                a.getDate().format(MONTH_DAY),
                a.getPlace().getName(),
                a.getActivityName(),
                a.getCourtName(),
                a.getParticipantCount(),
                a.getCapacity(),
                names
        );
    }
}
