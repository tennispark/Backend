package kr.tennispark.advertisement.admin.application;

import kr.tennispark.advertisement.admin.application.exception.AdvertisementLimitExceededException;
import kr.tennispark.advertisement.admin.infrastructure.repository.AdvertisementRepository;
import kr.tennispark.advertisement.admin.presentation.dto.response.GetAdvertisementResponseDTO;
import kr.tennispark.advertisement.common.domain.entity.Advertisement;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;
import kr.tennispark.qr.application.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertisementAdminService implements AdvertisementAdminUseCase {

    private static final String IMAGE_DIRECTORY = "advertisement";
    private static final Integer MAX_ADVERTISEMENTS_PER_POSITION = 3;
    private final AdvertisementRepository advertisementRepository;
    private final S3UploadService s3UploadService;

    @Override
    public void saveAdvertisement(MultipartFile imageFile, Position position) {
        validateAdvertisementLimit(position);

        String imageUrl = s3UploadService.uploadAdvertisementImage(imageFile, IMAGE_DIRECTORY);
        Advertisement advertisement = Advertisement.of(imageUrl, position);

        advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdvertisement(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.getById(advertisementId);
        advertisementRepository.delete(advertisement);
    }

    @Override
    public void updateAdvertisement(MultipartFile imageFile, Long advertisementId) {
        Advertisement advertisement = advertisementRepository.getById(advertisementId);
        String imageUrl = s3UploadService.uploadAdvertisementImage(imageFile, IMAGE_DIRECTORY);
        advertisement.updateImageUrl(imageUrl);

        advertisementRepository.save(advertisement);
    }

    @Override
    @Transactional(readOnly = true)
    public GetAdvertisementResponseDTO getAdvertisementsByPosition(Position position) {
        return GetAdvertisementResponseDTO.of(advertisementRepository.findAllByPosition(position));
    }

    private void validateAdvertisementLimit(Position position) {
        if (advertisementRepository.countByPosition(position) >= MAX_ADVERTISEMENTS_PER_POSITION) {
            throw new AdvertisementLimitExceededException();
        }
    }
}
