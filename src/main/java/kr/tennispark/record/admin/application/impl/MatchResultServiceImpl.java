package kr.tennispark.record.admin.application.impl;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.record.admin.application.MatchResultService;
import kr.tennispark.record.admin.infrastructure.MatchResultRepository;
import kr.tennispark.record.admin.infrastructure.MemberRecordRepository;
import kr.tennispark.record.admin.presentation.dto.request.SaveMatchResultRequestDTO;
import kr.tennispark.record.common.domain.entity.MatchResult;
import kr.tennispark.record.common.domain.entity.association.MemberRecord;
import kr.tennispark.record.common.domain.entity.exception.InvalidMatchResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchResultServiceImpl implements MatchResultService {

    private final MemberRepository memberRepository;
    private final MatchResultRepository matchResultRepository;
    private final MemberRecordRepository memberRecordRepository;

    @Override
    public void saveMatchResult(SaveMatchResultRequestDTO request) {
        validateMemberDuplication(request.teamA().playerIds(), request.teamB().playerIds());

        List<Member> teamAMembers = fetchMembersByIds(request.teamA().playerIds());
        List<Member> teamBMembers = fetchMembersByIds(request.teamB().playerIds());

        MatchResult matchResult = MatchResult.of(
                request.teamA().score(),
                request.teamB().score(),
                request.matchDate()
        );
        matchResultRepository.save(matchResult);

        boolean isTeamAWin = request.teamA().score() > request.teamB().score();

        saveMemberRecords(teamAMembers, matchResult, isTeamAWin, request.teamA().score());
        saveMemberRecords(teamBMembers, matchResult, !isTeamAWin, request.teamB().score());
    }

    private List<Member> fetchMembersByIds(List<Long> ids) {
        return ids.stream()
                .map(memberRepository::getById)
                .toList();
    }

    private void validateMemberDuplication(List<Long> teamAIds, List<Long> teamBIds) {
        if (teamAIds.stream().anyMatch(teamBIds::contains)) {
            throw new InvalidMatchResultException("팀 A와 팀 B에 중복된 선수가 있습니다.");
        }
    }

    private void saveMemberRecords(List<Member> members, MatchResult matchResult, boolean isWinner, int score) {
        members.forEach(member -> {
            MemberRecord record = MemberRecord.of(member, matchResult, isWinner, score);
            memberRecordRepository.save(record);
        });
    }
}