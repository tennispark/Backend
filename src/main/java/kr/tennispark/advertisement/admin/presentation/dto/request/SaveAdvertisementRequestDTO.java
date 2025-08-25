package kr.tennispark.advertisement.admin.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;

public record SaveAdvertisementRequestDTO(
        @NotNull(message = "자리는 필수값입니다.")
        Position position,

        @NotNull(message = "링크 url은 필수값입니다.")
        String linkUrl
) {
}
