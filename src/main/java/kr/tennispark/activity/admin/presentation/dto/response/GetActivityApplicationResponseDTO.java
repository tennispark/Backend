package kr.tennispark.activity.admin.presentation.dto.response;

import java.time.LocalDate;
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
                        activity.getDate(),
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
            LocalDate date,
            LocalTime startAt,
            LocalTime endAt,
            int participantCount,
            int capacity
    ) {
        public static ActivityApplicationDTO of(
                Long id,
                String placeName,
                String courtName,
                LocalDate date,
                LocalTime startAt,
                LocalTime endAt,
                int participantCount,
                int capacity
        ) {
            return new ActivityApplicationDTO(id, placeName, courtName, date, startAt, endAt, participantCount,
                    capacity);
        }

    }
}
