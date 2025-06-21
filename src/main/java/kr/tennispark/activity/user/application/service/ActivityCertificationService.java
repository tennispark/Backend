package kr.tennispark.activity.user.application.service;

import kr.tennispark.activity.common.domain.ActivityCertification;
import kr.tennispark.activity.user.infrastructure.repository.ActivityCertificationRepository;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.qr.application.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityCertificationService {

    private static final String IMAGE_PREFIX = "cert/";
    private final ActivityCertificationRepository certificationRepository;
    private final S3UploadService uploadService;

    @Transactional
    public void certifyActivity(Member member, MultipartFile image) {
        String imageUrl = uploadService.uploadImageFile(image, IMAGE_PREFIX);

        ActivityCertification cert =
                ActivityCertification.of(member, imageUrl);
        certificationRepository.save(cert);
    }
}
