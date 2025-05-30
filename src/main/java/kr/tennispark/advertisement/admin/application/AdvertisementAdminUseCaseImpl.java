package kr.tennispark.advertisement.admin.application;

import kr.tennispark.advertisement.admin.application.exception.AdvertisementLimitExceededException;
import kr.tennispark.advertisement.admin.infrastructure.repository.AdvertisementRepository;
import kr.tennispark.advertisement.admin.presentation.dto.request.SaveAdvertisementRequestDTO;
import kr.tennispark.advertisement.admin.presentation.dto.response.GetAdvertisementResponseDTO;
import kr.tennispark.advertisement.common.domain.entity.Advertisement;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertisementAdminUseCaseImpl implements AdvertisementAdminUseCase {

    private static final Integer MAX_ADVERTISEMENTS_PER_POSITION = 3;

    private final AdvertisementRepository advertisementRepository;

    @Override
    public void saveAdvertisement(SaveAdvertisementRequestDTO request) {
        validateAdvertisementLimit(request.position());

        Advertisement advertisement = Advertisement.of(request.imageUrl(), request.position());

        advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdvertisement(Long advertisementId) {
        Advertisement advertisement = advertisementRepository.getById(advertisementId);
        advertisementRepository.delete(advertisement);
    }

    @Override
    public void updateAdvertisement(SaveAdvertisementRequestDTO request, Long advertisementId) {
        Advertisement advertisement = advertisementRepository.getById(advertisementId);
        advertisement.updateImageUrl(request.imageUrl());
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
