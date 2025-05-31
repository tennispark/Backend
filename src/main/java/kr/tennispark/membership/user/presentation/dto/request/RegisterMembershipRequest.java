package kr.tennispark.membership.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.tennispark.membership.common.domain.entity.enums.ActivityDuration;
import kr.tennispark.membership.common.domain.entity.enums.CourtType;
import kr.tennispark.membership.common.domain.entity.enums.MembershipType;

public record RegisterMembershipRequest(
        @NotNull(message = "멤버십 타입은 필수입니다.") MembershipType membershipType,
        @NotBlank(message = "멤버십 가입 이유는 필수입니다.") @Size(max = 100) String reason,
        @NotNull(message = "코트 타입은 필수입니다.") CourtType courtType,
        @NotNull(message = "멤버십 기간은 필수입니다.") ActivityDuration period,
        @Size(max = 50) String recommender
) {
}

