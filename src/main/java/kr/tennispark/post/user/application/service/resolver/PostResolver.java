package kr.tennispark.post.user.application.service.resolver;

import java.util.List;
import kr.tennispark.common.application.S3UploadService;
import kr.tennispark.post.common.domain.entity.vo.Photos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class PostResolver {
    private static final int MAX_PHOTOS = 3;
    private static final String IMAGE_PREFIX = "post/";

    private final S3UploadService uploadService;

    public Photos toPhotos(List<MultipartFile> photoFiles) {
        if (photoFiles == null || photoFiles.isEmpty()) {
            return Photos.of(null);
        }
        if (photoFiles.size() > MAX_PHOTOS) {
            throw new IllegalArgumentException("사진은 최대 " + MAX_PHOTOS + "개까지 업로드 가능합니다.");
        }
        List<String> photoUrls = uploadPhotos(photoFiles);
        return Photos.of(photoUrls);
    }

    public List<String> uploadPhotos(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return List.of();
        }
        return files.stream()
                .map(file -> uploadService.uploadImageFile(file, IMAGE_PREFIX))
                .toList();
    }
}
