package kr.tennispark.event.user.application.impl;

import kr.tennispark.event.admin.infrastructure.repository.EventRepository;
import kr.tennispark.event.common.domain.Event;
import kr.tennispark.event.common.domain.association.EventApplication;
import kr.tennispark.event.user.application.EventMemberUseCase;
import kr.tennispark.event.user.application.exception.AlreadyAttendEventException;
import kr.tennispark.event.user.infrastructure.repository.EventApplicationRepository;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.PointReason;
import kr.tennispark.members.common.domain.entity.vo.Point;
import kr.tennispark.members.common.domain.entity.vo.PointHistory;
import kr.tennispark.members.user.infrastructure.repository.PointHistoryRepository;
import kr.tennispark.members.user.infrastructure.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventMemberUseCaseImpl implements EventMemberUseCase {

    private final EventRepository eventRepository;
    private final EventApplicationRepository eventApplicationRepository;
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public void attendEvent(Long eventId, Member member) {
        Event event = eventRepository.getById(eventId);
        validateNotAlreadyAttended(event, member);

        applyForEvent(member, event);
        earnEventPoint(member, event);
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

    private void earnEventPoint(Member member, Event event) {
        Point point = pointRepository.getByMemberId(member.getId());

        point.addPoint(event.getPoint());
        pointHistoryRepository.save(PointHistory.of(point, event.getPoint(), PointReason.EVENT));
    }
}