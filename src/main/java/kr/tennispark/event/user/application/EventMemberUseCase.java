package kr.tennispark.event.user.application;

import kr.tennispark.members.common.domain.entity.Member;

public interface EventMemberUseCase {

    void attendEvent(Long eventId, Member member);
}
