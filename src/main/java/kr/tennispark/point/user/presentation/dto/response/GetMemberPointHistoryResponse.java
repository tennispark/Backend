package kr.tennispark.point.user.presentation.dto.response;

import java.util.List;
import kr.tennispark.point.common.domain.entity.PointHistory;

public record GetMemberPointHistoryResponse(
            List<PointHistoryDto> histories
    ) {
        public static GetMemberPointHistoryResponse of(List<PointHistory> pointHistories) {
            List<PointHistoryDto> dtoList = pointHistories.stream()
                    .map(PointHistoryDto::of)
                    .toList();
            return new GetMemberPointHistoryResponse(dtoList);
        }
}