package kr.tennispark.post.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReportRequest(
        @NotBlank(message = "신고 사유는 필수입니다.")
        @Size(max = 1000, message = "신고 사유는 1000자 이하여야 합니다.")
        String reason
) {
}

