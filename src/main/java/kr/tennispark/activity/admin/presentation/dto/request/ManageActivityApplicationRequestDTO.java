package kr.tennispark.activity.admin.presentation.dto.request;

import kr.tennispark.activity.common.domain.enums.ApplicationStatus;

public record ManageActivityApplicationRequestDTO(
        ApplicationStatus applicationStatus
) {
}
