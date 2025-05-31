package kr.tennispark.event.user.application.impl;

import kr.tennispark.event.admin.infrastructure.repository.EventRepository;
import kr.tennispark.event.common.domain.Event;
import kr.tennispark.event.common.domain.association.EventApplication;
import kr.tennispark.event.user.application.EventMemberUseCase;
import kr.tennispark.event.user.application.exception.AlreadyAttendEventException;
import kr.tennispark.event.user.infrastructure.repository.EventApplicationRepository;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.application.service.MemberService;
import kr.tennispark.point.common.domain.entity.enums.PointReason;
import kr.tennispark.point.user.application.service.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventMemberUseCaseImpl implements EventMemberUseCase {

    private final EventRepository eventRepository;
    private final EventApplicationRepository eventApplicationRepository;

    private final UserPointService pointService;

    @Override
    public void attendEvent(Long eventId, Member member) {
        Event event = eventRepository.getById(eventId);
        validateNotAlreadyAttended(event, member);

        applyForEvent(member, event);
        pointService.applyPoint(member, event.getPoint(), PointReason.EVENT);
    }

    private void validateNotAlreadyAttended(Event event, Member member) {
        if (eventApplicationRepository.existsByEventAndMember(event, member)) {
            throw new AlreadyAttendEventException();
        }
    }

    private void applyForEvent(Member member, Event event) {
        EventApplication application = EventApplication.of(event, member);
        eventApplicationRepository.save(application);
    }
}
