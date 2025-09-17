package kr.tennispark.post.user.presentation.dto.request;

import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record UpdatePostMultiPart(
        SavePostDTO data,
        @Size(max = 3) List<MultipartFile> photos
) {
}
