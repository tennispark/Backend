package kr.tennispark.activity.admin.presentation.dto.response;

import java.time.LocalTime;
import kr.tennispark.activity.common.domain.ActivityInfo;
import org.springframework.data.domain.Page;

public record GetActivityResponseInfoDTO(Page<ActivityDetails> acts) {

    public static GetActivityResponseInfoDTO of(Page<ActivityInfo> acts) {

        Page<ActivityDetails> actDetailsList = acts.map(act ->
                ActivityDetails.of(
                        act.getId(),
                        act.getPlace().getName(),
                        act.getTime().getBeginAt(),
                        act.getTime().getEndAt()
                )
        );

        return new GetActivityResponseInfoDTO(actDetailsList);
    }

    public record ActivityDetails(
            Long actId,
            String placeName,
            LocalTime beginAt,
            LocalTime endAt
    ) {

        public static ActivityDetails of(Long actId, String courtName, LocalTime beginAt, LocalTime endAt) {
            return new ActivityDetails(actId, courtName, beginAt, endAt);
        }
    }
}
