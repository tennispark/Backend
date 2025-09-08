package kr.tennispark.post.user.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record SavePostDTO(

        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        @Size(max = 3000, message = "내용은 3,000자 이하여야 합니다.")
        String content,

        @Size(max = 3, message = "삭제할 사진 인덱스는 최대 3개까지 지정할 수 있습니다.")
        List<@Min(1) @Max(3) Integer> deleteList) {
}
