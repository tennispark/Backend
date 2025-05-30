package kr.tennispark.members.user.application.service;

import kr.tennispark.event.common.domain.Event;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.association.Point;
import kr.tennispark.members.common.domain.entity.association.PointHistory;
import kr.tennispark.members.common.domain.entity.enums.PointReason;
import kr.tennispark.members.common.domain.entity.vo.Phone;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.members.user.infrastructure.repository.PointHistoryRepository;
import kr.tennispark.members.user.infrastructure.repository.PointRepository;
import kr.tennispark.members.user.presentation.dto.request.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public void createMember(RegisterMemberRequest request) {
        Phone phone = Phone.of(request.phoneNumber());

        Member member = Member.of(
                phone,
                request.name(),
                request.year(),
                request.tennisCareer(),
                request.recommender(),
                request.instagramId(),
                request.gender(),
                request.registrationSource()
        );
        memberRepository.save(member);
    }

    public boolean existsMemberByPhone(String phoneNumber) {
        return memberRepository.existsByPhone_Number(phoneNumber);
    }


    public void earnEventPoint(Member member, Event event) {
        Point point = pointRepository.getByMemberId(member.getId());

        point.addPoint(event.getPoint());
        pointHistoryRepository.save(PointHistory.of(point, member, event.getPoint(), PointReason.EVENT));
    }
}
