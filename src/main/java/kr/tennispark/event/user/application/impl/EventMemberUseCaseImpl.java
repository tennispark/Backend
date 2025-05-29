package kr.tennispark.event.user.application.impl;

import kr.tennispark.event.admin.infrastructure.repository.EventRepository;
import kr.tennispark.event.common.domain.Event;
import kr.tennispark.event.user.application.EventMemberUseCase;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventMemberUseCaseImpl implements EventMemberUseCase {

    private final EventRepository eventRepository;
    private final MemberService memberService;

    @Override
    public void attendEvent(Long eventId, Member member) {
        Event event = eventRepository.getById(eventId);

        memberService.addMemberPoint(member, event.getPoint());
    }
}
