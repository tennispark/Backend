package kr.tennispark.activity.user.presentation.dto.response;

import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.common.utils.KoreanFormat;

public record ActivityDTO(
        Long id,
        String date,
        String startAt,
        String endAt,
        int participantCount,
        int capacity,
        String courtType,
        PlaceDTO place,
        String courtName
) {
    public static ActivityDTO of(Activity a) {
        return new ActivityDTO(
                a.getId(),
                KoreanFormat.date(a.getDate()),
                KoreanFormat.time(a.getScheduledTime().getBeginAt()),
                KoreanFormat.time(a.getScheduledTime().getEndAt()),
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
