package kr.tennispark.activity.admin.presentation.dto.response;

import java.time.LocalTime;
import java.util.List;
import kr.tennispark.activity.common.domain.ActivityInfo;
import kr.tennispark.activity.common.domain.enums.ActivityName;
import kr.tennispark.activity.common.domain.enums.Days;
import org.springframework.data.domain.Page;

public record GetActivityResponseInfoDTO(Page<ActivityDetails> acts) {

    public static GetActivityResponseInfoDTO of(Page<ActivityInfo> acts) {

        Page<ActivityDetails> actDetailsList = acts.map(act ->
                ActivityDetails.of(
                        act.getId(),
                        act.getTime().getBeginAt(),
                        act.getTime().getEndAt(),
                        act.getActiveDays(),
                        act.getCapacity(),
                        act.getActivityName(),
                        act.getPlace().getName(),
                        act.getPlace().getAddress(),
                        act.getIsRecurring(),
                        act.getCourtName()
                )
        );

        return new GetActivityResponseInfoDTO(actDetailsList);
    }

    public record ActivityDetails(
            Long actId,
            LocalTime beginAt,
            LocalTime endAt,
            List<Days> activeDays,
            Integer participantCount,
            ActivityName courtType,
            String placeName,
            String address,
            Boolean isRecurring,
            String courtName

    ) {

        public static ActivityDetails of(
                Long actId, LocalTime beginAt, LocalTime endAt,
                List<Days> activeDays, Integer participantCount, ActivityName courtType,
                String placeName, String address, Boolean isRecurring, String courtName) {
            return new ActivityDetails(
                    actId,
                    beginAt,
                    endAt,
                    activeDays,
                    participantCount,
                    courtType,
                    placeName,
                    address,
                    isRecurring,
                    courtName
            );
        }
    }
}
