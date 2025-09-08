package kr.tennispark.post.user.application.service.resolver;

import java.util.List;
import java.util.Objects;
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

    public Photos replacePhotos(Photos origin,
                                List<MultipartFile> files) {
        if (files != null && !files.isEmpty()) {
            uploadService.deleteFiles(origin.toList());
            return toPhotos(files);
        }
        return origin;
    }

    public Photos applyDeleteAndAppend(Photos origin,
                                       List<Integer> deleteList,
                                       List<MultipartFile> newFiles) {
        Photos beforePhotos = (origin == null) ? Photos.of(null) : origin;
        Photos afterDelete = beforePhotos.deleteByIndices(deleteList);
        List<String> before = beforePhotos.toList();
        List<String> after = afterDelete.toList();

        if (!before.isEmpty()) {
            List<String> removed = before.stream()
                    .filter(url -> !after.contains(url))
                    .distinct()
                    .toList();
            if (!removed.isEmpty()) {
                uploadService.deleteFiles(removed);
            }
        }

        int capacity = Math.max(0, 3 - afterDelete.count());
        if (capacity == 0 || newFiles == null || newFiles.isEmpty()) {
            return afterDelete;
        }

        List<String> uploaded = newFiles.stream()
                .filter(Objects::nonNull)
                .filter(f -> !f.isEmpty())
                .limit(capacity)
                .map(f -> uploadService.uploadImageFile(f, IMAGE_PREFIX))
                .toList();

        return afterDelete.append(uploaded);
    }

    private List<String> uploadPhotos(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return List.of();
        }
        return files.stream()
                .map(file -> uploadService.uploadImageFile(file, IMAGE_PREFIX))
                .toList();
    }

    public void deleteIfExists(Photos photos) {
        if (photos != null && photos.toList().isEmpty()) {
            uploadService.deleteFiles(photos.toList());
        }
    }
}
