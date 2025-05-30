package kr.tennispark.advertisement.admin.presentation.dto.request;

import kr.tennispark.advertisement.common.domain.entity.enums.Position;

public record SaveAdvertisementRequestDTO(
        String imageUrl,
        Position position
) {
}
