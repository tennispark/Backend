package kr.tennispark.advertisement.admin.application;

import kr.tennispark.advertisement.admin.application.exception.AdvertisementLimitExceededException;
import kr.tennispark.advertisement.admin.infrastructure.repository.AdvertisementRepository;
import kr.tennispark.advertisement.admin.presentation.dto.request.SaveAdvertisementRequestDTO;
import kr.tennispark.advertisement.admin.presentation.dto.response.GetAdvertisementResponseDTO;
import kr.tennispark.advertisement.common.domain.entity.Advertisement;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;
import kr.tennispark.qr.application.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdvertisementAdminUseCaseImpl implements AdvertisementAdminUseCase {

    private static final Integer MAX_ADVERTISEMENTS_PER_POSITION = 3;
    private static final String IMAGE_PREFIX = "ads/";

    private final AdvertisementRepository advertisementRepository;
    private final S3UploadService uploadService;

    @Override
    @Transactional
    public void saveAdvertisement(SaveAdvertisementRequestDTO request, MultipartFile image) {
        validateAdvertisementLimit(request.position());
        String imageUrl = uploadService.uploadImageFile(image, IMAGE_PREFIX);

        Advertisement advertisement = Advertisement.of(imageUrl, request.position(), request.linkUrl());

        advertisementRepository.save(advertisement);
    }

    @Override
    @Transactional
    public void deleteAdvertisement(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.getById(advertisementId);
        advertisementRepository.delete(advertisement);
    }

    @Override
    @Transactional
    public void updateAdvertisement(SaveAdvertisementRequestDTO request, MultipartFile image, Long advertisementId) {
        Advertisement advertisement = advertisementRepository.getById(advertisementId);

        String imageUrl = uploadService.uploadImageFile(image, IMAGE_PREFIX);
        advertisement.updateAdvertisement(imageUrl, request.linkUrl());
    }

    @Override
    public GetAdvertisementResponseDTO getAdvertisementsByPosition(Position position) {
        return GetAdvertisementResponseDTO.of(advertisementRepository.findAllByPosition(position));
    }

    private void validateAdvertisementLimit(Position position) {
        if (advertisementRepository.countByPosition(position) >= MAX_ADVERTISEMENTS_PER_POSITION) {
            throw new AdvertisementLimitExceededException();
        }
    }
}
