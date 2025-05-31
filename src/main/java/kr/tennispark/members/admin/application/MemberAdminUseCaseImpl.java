package kr.tennispark.members.admin.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityApplicationRepository;
import kr.tennispark.members.admin.presentation.dto.response.GetMemberListResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetMonthlyMemberActivityStatsResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetOverallMemberStatsResponseDTO;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;
import kr.tennispark.members.common.domain.exception.NoSuchMemberException;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.members.user.infrastructure.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAdminUseCaseImpl implements MemberAdminUseCase {

    private final MemberRepository memberRepository;
    private final PointHistoryRepository pointHistoryRepository;

    private final UserActivityApplicationRepository activityApplicationRepository;
    private final ActivityRepository activityRepository;

    @Override
    public GetMonthlyMemberActivityStatsResponseDTO getMonthlyActivityStats(LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(LocalTime.MAX);

        int participantCount = countDistinctEventParticipants(start, end);
        int totalEarnedPoints = sumEarnedPoints(start, end);
        Member topEarner = findTopEarner(start, end);
        Member topSpender = findTopSpender(start, end);

        return GetMonthlyMemberActivityStatsResponseDTO.of(
                participantCount,
                totalEarnedPoints,
                topEarner.getName(),
                topSpender.getName());
    }

    @Override
    public GetOverallMemberStatsResponseDTO getOverallMemberStats() {
        long totalMembers = countTotalMembers();
        long totalActivityCount = countTotalEventApplications();
        Member topScorer = findTopLeagueMember();
        int topScore = getTopLeagueScore(topScorer.getId());

        return GetOverallMemberStatsResponseDTO.of(
                totalMembers,
                totalActivityCount,
                topScorer.getName(),
                topScore);
    }

    @Override
    public GetMemberListResponseDTO getMemberList(String name) {
        List<Member> members = memberRepository.findByNameContaining(name);
        return GetMemberListResponseDTO.of(members);
    }

    private int countDistinctEventParticipants(LocalDateTime start, LocalDateTime end) {
        return activityApplicationRepository.countDistinctMemberIdByCreatedAtBetween(start, end);
    }

    private int sumEarnedPoints(LocalDateTime start, LocalDateTime end) {
        return pointHistoryRepository.sumAmountByCreatedAtBetweenAndAmountGreaterThan(start, end);
    }

    private Member findTopEarner(LocalDateTime start, LocalDateTime end) {
        return pointHistoryRepository.findTopEarner(start, end)
                .orElseThrow(() -> new NoSuchMemberException("해당 기간에 포인트를 적립한 회원이 없습니다."));
    }

    private Member findTopSpender(LocalDateTime start, LocalDateTime end) {
        return pointHistoryRepository.findTopSpender(start, end)
                .orElseThrow(() -> new NoSuchMemberException("해당 기간에 포인트를 사용한 회원이 없습니다."));
    }

    private long countTotalMembers() {
        return memberRepository.countByMemberShipType(MemberShipType.MEMBERSHIP);
    }

    private long countTotalEventApplications() {
        return activityRepository.count();
    }

    private Member findTopLeagueMember() {
        return memberRepository.findTopScorerMember()
                .orElseThrow(() -> new NoSuchMemberException("리그에서 활동한 회원이 없습니다."));
    }

    private int getTopLeagueScore(Long memberId) {
        return memberRepository.sumScoreByMemberId(memberId);
    }
}
