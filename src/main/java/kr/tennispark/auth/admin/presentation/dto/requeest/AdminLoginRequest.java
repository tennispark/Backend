package kr.tennispark.auth.admin.presentation.dto.requeest;

import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequest(

        @NotBlank(message = "아이디는 필수입니다.")
        String id,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
}
