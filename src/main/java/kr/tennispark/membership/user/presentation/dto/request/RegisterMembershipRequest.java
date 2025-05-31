package kr.tennispark.membership.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.tennispark.membership.common.domain.entity.enums.ActivityDuration;
import kr.tennispark.membership.common.domain.entity.enums.CourtType;
import kr.tennispark.membership.common.domain.entity.enums.MembershipType;

public record RegisterMembershipRequest(
        @NotNull MembershipType membershipType,
        @NotBlank @Size(max = 100) String reason,
        @NotNull CourtType courtType,
        @NotNull ActivityDuration period,
        @Size(max = 50) String recommender
) {
}

