package kr.tennispark.post.user.presentation.dto.request;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public record UpdateCommentMultiPart(
        @Valid UpdateCommentDTO data,
        MultipartFile photo
) {
}
