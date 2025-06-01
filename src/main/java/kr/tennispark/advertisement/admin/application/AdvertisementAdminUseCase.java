package kr.tennispark.advertisement.admin.application;

import kr.tennispark.advertisement.admin.presentation.dto.request.SaveAdvertisementRequestDTO;
import kr.tennispark.advertisement.admin.presentation.dto.response.GetAdvertisementResponseDTO;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;

public interface AdvertisementAdminUseCase {

    void saveAdvertisement(SaveAdvertisementRequestDTO request);

    void deleteAdvertisement(Long advertisementId);

    void updateAdvertisement(SaveAdvertisementRequestDTO request, Long advertisementId);

    GetAdvertisementResponseDTO getAdvertisementsByPosition(Position position);

}
