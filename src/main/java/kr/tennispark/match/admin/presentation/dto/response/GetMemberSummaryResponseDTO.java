package kr.tennispark.match.admin.presentation.dto.response;

import java.util.List;

public record GetMemberSummaryResponseDTO(
        List<MemberSummaryDTO> members
) {

    public static GetMemberSummaryResponseDTO of(List<MemberSummaryDTO> members) {
        return new GetMemberSummaryResponseDTO(members);
    }

    public record MemberSummaryDTO(
            Long memberId,
            String name
    ) {
        public static MemberSummaryDTO of(Long memberId, String name) {
            return new MemberSummaryDTO(memberId, name);
        }
    }
}
