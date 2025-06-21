package kr.tennispark.members.user.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kr.tennispark.members.common.domain.entity.enums.Gender;
import kr.tennispark.members.common.domain.entity.enums.RegistrationSource;

public record RegisterMemberRequest(

        @NotBlank(message = "휴대폰 번호는 필수입니다.")
        @Pattern(regexp = "^010\\d{8}$", message = "휴대폰 번호는 010으로 시작하고 숫자 8자여야 합니다.")
        String phoneNumber,

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "성별은 필수입니다.")
        Gender gender,

        @NotNull(message = "테니스 경력은 필수입니다.")
        @Min(0)
        Integer tennisCareer,

        @NotNull(message = "출생 연도는 필수입니다.")
        @Min(value = 1900, message = "출생 연도는 1900년 이후여야 합니다.")
        @Max(value = 2025, message = "출생 연도가 유효하지 않습니다.")
        Integer year,

        @NotNull(message = "가입 경로는 필수입니다.")
        RegistrationSource registrationSource,

        String recommender,

        @NotBlank(message = "인스타그램 ID는 필수입니다.")
        String instagramId

) {
}
