package kr.tennispark.post.user.presentation.dto.request;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public record RegisterCommentMultiPart(
        @Valid CreateCommentDTO data,
        MultipartFile photo
) {
}
