package kr.tennispark.web.member.presentation.dto;

import java.util.List;

public record GetMemberManagementResponse(
        List<MemberManagementRowDTO> rows
) {
}
