package kr.tennispark.match.admin.presentation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record SaveMatchResultRequestDTO(
        @Valid MatchResultDTO teamA,
        @Valid MatchResultDTO teamB,
        @NotNull(message = "경기 날짜는 필수입니다.") LocalDate matchDate) {

    public record MatchResultDTO(
            @NotEmpty(message = "플레이어 ID는 필수입니다.")
            @Size(min = 2, max = 2, message = "플레이어 ID는 팀별 2개만 입력 가능합니다.")
            List<Long> playerIds,

            @NotNull(message = "경기 스코어는 필수입니다.") Integer score
    ) {
    }
}
