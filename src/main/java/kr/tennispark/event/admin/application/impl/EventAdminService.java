package kr.tennispark.event.admin.application.impl;

import kr.tennispark.event.admin.application.EventAdminUseCase;
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

    @Value("${qr.url.prefix}")
    private String qrImageUrlPrefix;

    @Value("${qr.url.suffix}")
    private String URL_SUFFIX;

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
        Event event = Event.of(
                request.title(),
                request.content(),
                request.point()
        );

        eventRepository.save(event);

        String qrImageUrl = generateQrImageUrl(event.getId());
        event.attachQrImageUrl(qrImageUrl);

        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void modifyEventDetails(Long eventId, ManageEventRequestDTO request) {
        Event event = eventRepository.getById(eventId);

        event.modifyEventDetails(
                request.title(),
                request.content(),
                request.point()
        );

        eventRepository.save(event);

    }

    @Override
    @Transactional
    public void removeEvent(Long eventId) {
        Event event = eventRepository.getById(eventId);

        eventRepository.delete(event);
    }

    private String generateQrImageUrl(Long eventId) {
        String targetUrl = qrImageUrlPrefix + URL_SUFFIX + eventId;
        return qrService.generateAndUploadQr(targetUrl);
    }
}
