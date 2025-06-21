package kr.tennispark.advertisement.admin.application;

import kr.tennispark.advertisement.admin.presentation.dto.request.SaveAdvertisementRequestDTO;
import kr.tennispark.advertisement.admin.presentation.dto.response.GetAdvertisementResponseDTO;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertisementAdminUseCase {

    void saveAdvertisement(SaveAdvertisementRequestDTO request, MultipartFile image);

    void deleteAdvertisement(Long advertisementId);

    void updateAdvertisement(SaveAdvertisementRequestDTO request, MultipartFile image, Long advertisementId);

    GetAdvertisementResponseDTO getAdvertisementsByPosition(Position position);

}
