package kr.tennispark.point.user.presentation.dto.response;

import kr.tennispark.point.common.domain.entity.PointHistory;
import kr.tennispark.point.common.domain.entity.enums.PointReason;

import java.time.format.DateTimeFormatter;

public record PointHistoryDto(
        Long historyId,
        String title,
        Integer point,
        String type,
        String date
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");


    public static PointHistoryDto fromEntity(PointHistory history) {
        PointReason reason = history.getReason();
        String title = String.format("%s - %s", reason.getDefaultTitle(), history.getDetail());
        String type = reason.makeType();
        String formattedDate = history.getCreatedAt().format(FORMATTER);

        return new PointHistoryDto(
                history.getId(),
                title,
                history.getAmount(),
                type,
                formattedDate
        );
    }
}

