package kr.tennispark.activity.user.presentation.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import kr.tennispark.activity.common.domain.Activity;

public record ActivityDTO(
        Long activityId,
        String date,
        String startAt,
        String endAt,
        int participantCount,
        int capacity,
        String courtType,
        PlaceDTO place,
        String courtName
) {
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("MM.dd(E)", Locale.KOREAN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public static ActivityDTO of(Activity a) {
        return new ActivityDTO(
                a.getId(),
                a.getDate().format(DATE_FMT),
                a.getScheduledTime().getBeginAt().format(TIME_FMT),
                a.getScheduledTime().getEndAt().format(TIME_FMT),
                a.getParticipantCount(),
                a.getCapacity(),
                a.getActivityName().name(),
                new PlaceDTO(a.getPlace().getName(), a.getPlace().getAddress()),
                a.getCourtName()
        );
    }

    public record PlaceDTO(String name, String address) {
    }
}
