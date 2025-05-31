package kr.tennispark.activity.admin.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import kr.tennispark.activity.common.domain.enums.ActivityName;
import kr.tennispark.activity.common.domain.enums.CourtType;

public record ManageActivityInfoRequestDTO(
        @NotNull(message = "활동 시작 시간은 필수입니다.") LocalTime beginAt,
        @NotNull(message = "활동 종료 시간은 필수입니다.") LocalTime endAt,
        @NotEmpty(message = "활동 반복 요일은 필수입니다.") List<String> activeDays,
        @NotNull(message = "활동 참여 인원은 필수입니다.") Integer participantCount,
        @NotNull(message = "코트 타입은 필수입니다.") ActivityName courtType,
        @NotBlank(message = "활동 장소 이름은 필수입니다.") String placeName,
        @NotBlank(message = "활동 장소 주소는 필수입니다.") String address,
        @NotNull(message = "활동 반복 유무는 필수입니다.") Boolean isRecurring,
        @NotBlank(message = "코트 이름은 필수입니다.") String courtName
) {
}
