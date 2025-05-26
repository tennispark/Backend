package kr.tennispark.event.application.impl;

import kr.tennispark.event.application.EventAdminUseCase;
import kr.tennispark.event.domain.Event;
import kr.tennispark.event.infrastructure.repository.EventRepository;
import kr.tennispark.event.presentation.dto.request.ManageEventRequestDTO;
import kr.tennispark.event.presentation.dto.response.GetEventResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventAdminService implements EventAdminUseCase {

    private final EventRepository eventRepository;

    @Override
    public GetEventResponseDTO getAllEvents(int page, int size) {
        Page<Event> eventPage = eventRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        return GetEventResponseDTO.of(eventPage);
    }

    @Override
    @Transactional
    public void registerEvent(ManageEventRequestDTO request) {
        Event event = Event.of(
                request.name(),
                request.detail(),
                request.point(),
                request.imageUrl()
        );

        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void modifyEventDetails(Long eventId, ManageEventRequestDTO request) {
        Event event = eventRepository.getById(eventId);

        event.modifyEventDetails(
                request.name(),
                request.detail(),
                request.point(),
                request.imageUrl()
        );

        eventRepository.save(event);

    }

    @Override
    @Transactional
    public void removeEvent(Long eventId) {
        Event event = eventRepository.getById(eventId);

        eventRepository.delete(event);
    }
}
