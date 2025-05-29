package kr.tennispark.record.admin.presentation.dto.request;

import java.time.LocalDate;
import java.util.List;

public record SaveMatchResultRequestDTO(MatchResultDTO teamA, MatchResultDTO teamB, LocalDate matchDate) {

    public record MatchResultDTO(
            List<Long> playerIds,
            Integer score
    ) {
    }
}
