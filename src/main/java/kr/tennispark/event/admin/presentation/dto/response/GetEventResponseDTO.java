package kr.tennispark.event.admin.presentation.dto.response;

import kr.tennispark.event.common.domain.Event;
import org.springframework.data.domain.Page;

public record GetEventResponseDTO(Page<EventDetails> events) {

    public static GetEventResponseDTO of(Page<Event> events) {

        Page<EventDetails> eventDetails = events.map(event ->
                EventDetails.of(
                        event.getId(),
                        event.getTitle(),
                        event.getContent(),
                        event.getPoint(),
                        event.getImageUrl()
                )
        );

        return new GetEventResponseDTO(eventDetails);
    }

    public record EventDetails(
            Long id,
            String title,
            String content,
            Integer point,
            String imageUrl
    ) {
        public static EventDetails of(
                Long id,
                String title,
                String content,
                Integer point,
                String imageUrl
        ) {
            return new EventDetails(id, title, content, point, imageUrl);
        }
    }
}
