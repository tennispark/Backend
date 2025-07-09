package kr.tennispark.event.user.application.impl;

import kr.tennispark.event.admin.infrastructure.repository.EventRepository;
import kr.tennispark.event.common.domain.Event;
import kr.tennispark.event.common.domain.association.EventApplication;
import kr.tennispark.event.user.application.EventMemberUseCase;
import kr.tennispark.event.user.application.exception.AlreadyAttendEventException;
import kr.tennispark.event.user.infrastructure.repository.EventApplicationRepository;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.common.application.service.PointService;
import kr.tennispark.point.common.domain.entity.enums.PointReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventMemberUseCaseImpl implements EventMemberUseCase {

    private final EventRepository eventRepository;
    private final EventApplicationRepository eventApplicationRepository;

    private final PointService pointService;

    @Override
    public void attendEvent(Long eventId, Member member) {
        Event event = eventRepository.getById(eventId);
        validateNotAlreadyAttended(member, event);

        applyForEvent(member, event);
        pointService.applyPoint(member, event.getPoint(), PointReason.EVENT, event.getTitle());
    }

    private void validateNotAlreadyAttended(Member member, Event event) {
        if (eventApplicationRepository.existsByMemberToday(member, event)) {
            throw new AlreadyAttendEventException();
        }
    }

    private void applyForEvent(Member member, Event event) {
        EventApplication application = EventApplication.of(event, member);
        eventApplicationRepository.save(application);
    }
}
