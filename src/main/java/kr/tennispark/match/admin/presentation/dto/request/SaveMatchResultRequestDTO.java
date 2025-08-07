package kr.tennispark.match.admin.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SaveMatchResultRequestDTO(
        @NotNull Long memberId,
        @NotNull Integer myScore,
        @NotNull Integer opponentScore,
        @NotNull LocalDate matchDate
) {
}
