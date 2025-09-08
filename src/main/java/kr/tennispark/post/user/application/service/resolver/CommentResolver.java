package kr.tennispark.post.user.application.service.resolver;

import java.util.List;
import kr.tennispark.common.application.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class CommentResolver {
    private static final String IMAGE_PREFIX = "comment/";

    private final S3UploadService uploadService;
    
    public String toNewPhotoUrl(MultipartFile photo) {
        if (photo == null || photo.isEmpty()) {
            return null;
        }
        return uploadService.uploadImageFile(photo, IMAGE_PREFIX);
    }

    public String resolvePhotoForUpdate(String originUrl, MultipartFile newPhoto, Boolean deletePhoto) {
        boolean wantDelete = Boolean.TRUE.equals(deletePhoto);
        boolean hasNewFile = newPhoto != null && !newPhoto.isEmpty();

        String result = originUrl;

        if (wantDelete && result != null) {
            uploadService.deleteFiles(List.of(result));
            result = null;
        }

        if (hasNewFile) {
            if (!wantDelete && result != null) {
                uploadService.deleteFiles(List.of(result));
            }
            result = uploadService.uploadImageFile(newPhoto, IMAGE_PREFIX);
        }

        return result;
    }

    public void deleteIfExists(String url) {
        if (url != null && !url.isBlank()) {
            uploadService.deleteFiles(List.of(url));
        }
    }
}
