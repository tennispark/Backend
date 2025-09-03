package kr.tennispark.web.member.application.service;

import java.util.List;
import kr.tennispark.match.common.infrastructure.repository.MatchPointRankingRepository;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.web.member.infrastructure.repository.WebAdminMemberRepository;
import kr.tennispark.web.member.presentation.dto.GetMemberManagementResponse;
import kr.tennispark.web.member.presentation.dto.MemberManagementRowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WebMemberManagementQueryService {

    private final WebAdminMemberRepository memberRepository;
    private final MatchPointRankingRepository rankingRepository;

    public GetMemberManagementResponse getAllActiveMembers() {
        List<Member> members = memberRepository.findAllActiveMember();

        List<MemberManagementRowDTO> rows = members.stream()
                .map(m -> MemberManagementRowDTO.of(m, rankingRepository.getRank(m.getId())))
                .toList();

        return new GetMemberManagementResponse(rows);
    }
}
