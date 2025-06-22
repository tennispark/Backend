package kr.tennispark.members.user.application.service;

import io.micrometer.common.util.StringUtils;
import kr.tennispark.match.common.domain.entity.enums.MatchOutcome;
import kr.tennispark.match.common.infrastructure.repository.MatchPointRankingRepository;
import kr.tennispark.match.user.infrastructure.repository.UserMatchParticipationRepository;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.vo.Phone;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.members.user.presentation.dto.request.RegisterMemberRequest;
import kr.tennispark.members.user.presentation.dto.response.GetMemberMatchRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MatchPointRankingRepository rankingRepository;
    private final UserMatchParticipationRepository participationRepository;

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
        updateFcmToken(request, member);
        memberRepository.save(member);
    }

    public boolean existsMemberByPhone(String phoneNumber) {
        return memberRepository.existsByPhone_Number(phoneNumber);
    }

    public GetMemberMatchRecordResponse getMemberMatchRecord(Member member) {
        long wins = participationRepository.countByMemberIdAndMatchOutcome(member.getId(), MatchOutcome.WIN);
        long draws = participationRepository.countByMemberIdAndMatchOutcome(member.getId(), MatchOutcome.DRAW);
        long losses = participationRepository.countByMemberIdAndMatchOutcome(member.getId(), MatchOutcome.LOSE);

        int matchPoint = member.getMatchPoint();
        long ranking = rankingRepository.getRank(member.getId());

        return GetMemberMatchRecordResponse.of(wins, draws, losses, matchPoint, ranking);
    }

    private void updateFcmToken(RegisterMemberRequest request, Member member) {
        if (!StringUtils.isBlank(request.fcmToken())) {
            member.updateFcmToken(request.fcmToken());
        }
    }
}
