package kr.tennispark.event.presentation.dto.response;

import kr.tennispark.event.domain.Event;
import org.springframework.data.domain.Page;

public record GetEventResponseDTO(Page<EventDetails> events) {

    public static GetEventResponseDTO of(Page<Event> events) {

        Page<EventDetails> eventDetails = events.map(event ->
                EventDetails.of(
                        event.getId(),
                        event.getName(),
                        event.getDetail(),
                        event.getPoint(),
                        event.getImageUrl()
                )
        );

        return new GetEventResponseDTO(eventDetails);
    }

    public record EventDetails(
            Long id,
            String name,
            String detail,
            Integer point,
            String imageUrl
    ) {
        public static EventDetails of(
                Long id,
                String name,
                String detail,
                Integer point,
                String imageUrl
        ) {
            return new EventDetails(id, name, detail, point, imageUrl);
        }
    }
}
