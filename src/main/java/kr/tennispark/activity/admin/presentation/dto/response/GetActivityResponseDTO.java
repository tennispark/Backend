package kr.tennispark.activity.admin.presentation.dto.response;

import java.time.LocalTime;
import kr.tennispark.activity.common.domain.Activity;
import org.springframework.data.domain.Page;

public record GetActivityResponseDTO(Page<ActivityDetails> acts) {

    public static GetActivityResponseDTO of(Page<Activity> acts) {

        Page<ActivityDetails> actDetailsList = acts.map(act ->
                ActivityDetails.of(
                        act.getId(),
                        act.getCourtName(),
                        act.getActTime().getBeginAt(),
                        act.getActTime().getEndAt()
                )
        );

        return new GetActivityResponseDTO(actDetailsList);
    }

    public record ActivityDetails(
            Long actId,
            String courtName,
            LocalTime beginAt,
            LocalTime endAt
    ) {

        public static ActivityDetails of(Long actId, String courtName, LocalTime beginAt, LocalTime endAt) {
            return new ActivityDetails(actId, courtName, beginAt, endAt);
        }
    }
}
