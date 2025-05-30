package kr.tennispark.event.admin.application.impl;

import kr.tennispark.event.admin.application.EventAdminUseCase;
import kr.tennispark.event.admin.application.exception.MoreThan5EventException;
import kr.tennispark.event.admin.infrastructure.repository.EventRepository;
import kr.tennispark.event.admin.presentation.dto.request.ManageEventRequestDTO;
import kr.tennispark.event.admin.presentation.dto.response.GetEventResponseDTO;
import kr.tennispark.event.common.domain.Event;
import kr.tennispark.qr.application.QrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventAdminService implements EventAdminUseCase {

    private static final Integer MAX_EVENT_COUNT = 5;

    @Value("${qr.url.prefix}")
    private String qrUrlPrefix;

    @Value("${qr.url.suffix}")
    private String qrUrlSuffix;

    private final EventRepository eventRepository;
    private final QrService qrService;

    @Override
    public GetEventResponseDTO getAllEvents(int page, int size) {
        Page<Event> eventPage = eventRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        return GetEventResponseDTO.of(eventPage);
    }

    @Override
    @Transactional
    public void registerEvent(ManageEventRequestDTO request) {
        validateEventLimit();

        Event event = Event.of(request.title(), request.content(), request.point());
        eventRepository.saveAndFlush(event);

        attachQrToEvent(event);

        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void modifyEventDetails(Long eventId, ManageEventRequestDTO request) {
        Event event = eventRepository.getById(eventId);
        event.modifyEventDetails(request.title(), request.content(), request.point());
    }

    @Override
    @Transactional
    public void removeEvent(Long eventId) {
        Event event = eventRepository.getById(eventId);
        eventRepository.delete(event);
    }

    private void validateEventLimit() {
        if (eventRepository.countByStatus(true) >= MAX_EVENT_COUNT) {
            throw new MoreThan5EventException();
        }
    }

    private void attachQrToEvent(Event event) {
        String qrUrl = generateQrImageUrl(event.getId());
        event.attachQrImageUrl(qrUrl);
    }

    private String generateQrImageUrl(Long eventId) {
        String targetUrl = qrUrlPrefix + qrUrlSuffix + eventId;
        return qrService.generateAndUploadQr(targetUrl);
    }
}
