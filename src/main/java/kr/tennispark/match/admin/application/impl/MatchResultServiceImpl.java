package kr.tennispark.match.admin.application.impl;

import java.util.List;
import kr.tennispark.match.admin.application.MatchResultService;
import kr.tennispark.match.admin.infrastructure.MatchParticipationRepository;
import kr.tennispark.match.admin.infrastructure.MatchResultRepository;
import kr.tennispark.match.admin.presentation.dto.request.SaveMatchResultRequestDTO;
import kr.tennispark.match.admin.presentation.dto.response.GetMemberSummaryResponseDTO;
import kr.tennispark.match.admin.presentation.dto.response.GetMemberSummaryResponseDTO.MemberSummaryDTO;
import kr.tennispark.match.common.domain.entity.MatchResult;
import kr.tennispark.match.common.domain.entity.association.MatchParticipation;
import kr.tennispark.match.common.domain.entity.enums.MatchOutcome;
import kr.tennispark.match.common.domain.entity.exception.InvalidMatchResultException;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchResultServiceImpl implements MatchResultService {

    private final MemberRepository memberRepository;
    private final MatchResultRepository matchResultRepository;
    private final MatchParticipationRepository matchParticipationRepository;

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

        MatchOutcome teamAOutcome = determineMatchOutcome(request.teamA().score(), request.teamB().score());
        MatchOutcome teamBOutcome = determineMatchOutcome(request.teamB().score(), request.teamA().score());

        saveMemberRecords(teamAMembers, matchResult, teamAOutcome, request.teamA().score());
        saveMemberRecords(teamBMembers, matchResult, teamBOutcome, request.teamB().score());
    }

    @Override
    public GetMemberSummaryResponseDTO searchMemberNameForMatchResult(String memberName) {
        List<Member> members = memberRepository.findByNameContaining(memberName);

        return convertMembersToDTO(members);
    }

    private static GetMemberSummaryResponseDTO convertMembersToDTO(List<Member> members) {
        List<MemberSummaryDTO> memberSummaryDTOS = members.stream()
                .map(member -> MemberSummaryDTO.of(member.getId(), member.getName()))
                .toList();
        return GetMemberSummaryResponseDTO.of(memberSummaryDTOS);
    }

    private List<Member> fetchMembersByIds(List<Long> ids) {
        return memberRepository.findAllById(ids);
    }

    private void validateMemberDuplication(List<Long> teamAIds, List<Long> teamBIds) {
        if (teamAIds.stream().anyMatch(teamBIds::contains)) {
            throw new InvalidMatchResultException("팀 A와 팀 B에 중복된 선수가 있습니다.");
        }
    }

    private void saveMemberRecords(List<Member> members, MatchResult matchResult, MatchOutcome matchOutcome,
                                   int score) {
        members.forEach(member -> {
            MatchParticipation record = MatchParticipation.of(member, matchResult, matchOutcome, score);
            matchParticipationRepository.save(record);
        });
    }

    private MatchOutcome determineMatchOutcome(int teamAScore, int teamBScore) {
        if (teamAScore > teamBScore) {
            return MatchOutcome.WIN;
        } else if (teamAScore < teamBScore) {
            return MatchOutcome.LOSE;
        } else {
            return MatchOutcome.DRAW;
        }
    }
}