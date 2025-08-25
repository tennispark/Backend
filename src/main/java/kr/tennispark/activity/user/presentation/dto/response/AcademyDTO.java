package kr.tennispark.activity.user.presentation.dto.response;

import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.common.utils.KoreanFormat;

public record AcademyDTO(
        Long id,
        String date,
        String startAt,
        String endAt,
        int participantCount,
        int capacity,
        String lessonType,
        PlaceDTO place,
        String courtName
) {
    public static AcademyDTO of(Activity a) {
        return new AcademyDTO(
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
