package kr.tennispark.web.member.presentation.dto;

import jakarta.validation.constraints.NotNull;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;

public record UpdateMemberTypeRequest(
        @NotNull(message = "구분(멤버십/게스트)은 필수입니다.")
        MemberShipType type
) {
}
