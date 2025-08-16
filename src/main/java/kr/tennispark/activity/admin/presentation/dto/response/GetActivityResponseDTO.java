package kr.tennispark.activity.admin.presentation.dto.response;

import java.time.LocalTime;
import java.util.Map;
import kr.tennispark.activity.common.domain.Activity;
import org.springframework.data.domain.Page;

public record GetActivityResponseDTO(
        Page<ActivityDTO> applications
) {
    public static GetActivityResponseDTO of(
            Page<Activity> activities,
            Map<Long, Long> pendingCountMap
    ) {
        Page<ActivityDTO> applications = activities.map(activity ->
                ActivityDTO.of(
                        activity.getId(),
                        activity.getPlace().getName(),
                        activity.getCourtName(),
                        activity.getScheduledTime().getBeginAt(),
                        activity.getScheduledTime().getEndAt(),
                        activity.getApplicantCount(),
                        activity.getCapacity(),
                        pendingCountMap.getOrDefault(activity.getId(), 0L).intValue()
                )
        );
        return new GetActivityResponseDTO(applications);
    }

    public record ActivityDTO(
            Long id,
            String placeName,
            String courtName,
            LocalTime startAt,
            LocalTime endAt,
            int participantCount,
            int capacity,
            int pendingCount
    ) {
        public static ActivityDTO of(
                Long id,
                String placeName,
                String courtName,
                LocalTime startAt,
                LocalTime endAt,
                int participantCount,
                int capacity,
                int pendingCount
        ) {
            return new ActivityDTO(id, placeName, courtName, startAt, endAt, participantCount, capacity, pendingCount);
        }

    }
}
