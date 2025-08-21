package kr.tennispark.activity.admin.presentation.dto.response;

import java.time.LocalTime;
import kr.tennispark.activity.common.domain.Activity;
import org.springframework.data.domain.Page;

public record GetActivityApplicationResponseDTO(
        Page<ActivityApplicationDTO> applications
) {
    public static GetActivityApplicationResponseDTO of(Page<Activity> activities) {
        Page<ActivityApplicationDTO> applications = activities.map(activity ->
                ActivityApplicationDTO.of(
                        activity.getId(),
                        activity.getPlace().getName(),
                        activity.getCourtName(),
                        activity.getScheduledTime().getBeginAt(),
                        activity.getScheduledTime().getEndAt(),
                        activity.getApplicantCount(),
                        activity.getCapacity()
                )
        );
        return new GetActivityApplicationResponseDTO(applications);
    }

    public record ActivityApplicationDTO(
            Long id,
            String placeName,
            String courtName,
            LocalTime startAt,
            LocalTime endAt,
            int participantCount,
            int capacity
    ) {
        public static ActivityApplicationDTO of(
                Long id,
                String placeName,
                String courtName,
                LocalTime startAt,
                LocalTime endAt,
                int participantCount,
                int capacity
        ) {
            return new ActivityApplicationDTO(id, placeName, courtName, startAt, endAt, participantCount, capacity);
        }

    }
}
