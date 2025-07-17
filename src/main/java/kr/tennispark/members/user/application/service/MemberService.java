package kr.tennispark.members.user.application.service;

import java.util.List;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityApplicationRepository;
import kr.tennispark.match.common.domain.entity.enums.MatchOutcome;
import kr.tennispark.match.common.infrastructure.repository.MatchPointRankingRepository;
import kr.tennispark.match.user.infrastructure.repository.UserMatchParticipationRepository;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.vo.Phone;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.members.user.presentation.dto.request.RegisterMemberRequest;
import kr.tennispark.members.user.presentation.dto.response.GetMemberMatchRecordResponse;
import kr.tennispark.membership.infrastructure.repository.MembershipRepository;
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
    private final UserActivityApplicationRepository applicationRepository;
    private final MembershipRepository membershipRepository;

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
        member.updateFcmToken(request.fcmToken());
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

    @Transactional
    public void updateFcmToken(Member member, String fcmToken) {
        member.updateFcmToken(fcmToken);
        memberRepository.save(member);
    }

    @Transactional
    public void withdraw(Member member) {
        Member dbMember = memberRepository.getById(member.getId());

        dbMember.withdraw();

        membershipRepository.findByMember(member)
                .ifPresent(membershipRepository::delete);

        List<ActivityApplication> apps =
                applicationRepository.findActiveByMember(dbMember);

        apps.forEach(ActivityApplication::cancelByWithdrawal);
    }
}
