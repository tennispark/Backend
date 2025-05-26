package kr.tennispark.members.user.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginMemberRequest(
        @Email String phoneNumber,
        @NotEmpty String authCode) {
}
