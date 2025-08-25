package kr.tennispark.activity.admin.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;

public record ManageActivityApplicationRequestDTO(
        @NotNull(message = "신청 상태는 필수값입니다.")
        ApplicationStatus applicationStatus
) {
}
