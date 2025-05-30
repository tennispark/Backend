package kr.tennispark.advertisement.admin.application;

import kr.tennispark.advertisement.admin.presentation.dto.response.GetAdvertisementResponseDTO;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertisementAdminUseCase {

    void saveAdvertisement(MultipartFile imageFile, Position position);

    void deleteAdvertisement(Long advertisementId);

    void updateAdvertisement(MultipartFile imageFile, Long advertisementId);

    GetAdvertisementResponseDTO getAdvertisementsByPosition(Position position);

}
