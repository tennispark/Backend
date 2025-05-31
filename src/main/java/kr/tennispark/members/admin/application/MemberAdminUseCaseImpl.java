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

        String topEarnerName = toNameOrDefault(findTopEarner(start, end));
        String topSpenderName = toNameOrDefault(findTopSpender(start, end));

        return GetMonthlyMemberActivityStatsResponseDTO.of(
                participantCount,
                totalEarnedPoints,
                topEarnerName,
                topSpenderName
        );
    }

    @Override
    public GetOverallMemberStatsResponseDTO getOverallMemberStats() {
        long totalMembers = countTotalMembers();
        long totalActivityCount = countTotalEventApplications();

        Member topScorer = findTopLeagueMember();
        String topScorerName = toNameOrDefault(topScorer);
        int topScore = topScorer != null ? getTopLeagueScore(topScorer.getId()) : 0;

        return GetOverallMemberStatsResponseDTO.of(
                totalMembers,
                totalActivityCount,
                topScorerName,
                topScore
        );
    }

    @Override
    public GetMemberListResponseDTO getMemberList(String name) {
        List<Member> members = memberRepository.findByNameContaining(name);
        return GetMemberListResponseDTO.of(members);
    }

    // ===== 내부 계산 메서드 =====

    private int countDistinctEventParticipants(LocalDateTime start, LocalDateTime end) {
        return activityApplicationRepository.countDistinctMemberIdByCreatedAtBetween(start, end);
    }

    private int sumEarnedPoints(LocalDateTime start, LocalDateTime end) {
        return pointHistoryRepository.sumAmountByCreatedAtBetweenAndAmountGreaterThan(start, end);
    }

    private Member findTopEarner(LocalDateTime start, LocalDateTime end) {
        return pointHistoryRepository.findTopEarner(start, end).orElse(null);
    }

    private Member findTopSpender(LocalDateTime start, LocalDateTime end) {
        return pointHistoryRepository.findTopSpender(start, end).orElse(null);
    }

    private long countTotalMembers() {
        return memberRepository.countByMemberShipType(MemberShipType.MEMBERSHIP);
    }

    private long countTotalEventApplications() {
        return activityRepository.count();
    }

    private Member findTopLeagueMember() {
        return memberRepository.findTopScorerMember().orElse(null);
    }

    private int getTopLeagueScore(Long memberId) {
        return memberRepository.sumScoreByMemberId(memberId);
    }

    private String toNameOrDefault(Member member) {
        return member != null ? member.getName() : "없음";
    }
}