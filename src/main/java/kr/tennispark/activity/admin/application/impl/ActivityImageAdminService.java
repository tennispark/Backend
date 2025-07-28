package kr.tennispark.activity.admin.application.impl;


import kr.tennispark.activity.admin.infrastructure.repository.ActivityImageRepository;
import kr.tennispark.activity.common.domain.ActivityImage;
import kr.tennispark.qr.application.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityImageAdminService {

    private static final String IMAGE_PREFIX = "activity/";

    private final ActivityImageRepository activityImageRepository;
    private final S3UploadService uploadService;

    @Transactional
    public void saveActivityImage(MultipartFile image) {
        String imageUrl = uploadService.uploadImageFile(image, IMAGE_PREFIX);

        ActivityImage activityImage = ActivityImage.of(imageUrl);
        activityImageRepository.save(activityImage);
    }
}
