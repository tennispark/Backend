package kr.tennispark.advertisement.admin.presentation.dto.response;

import java.util.List;
import kr.tennispark.advertisement.common.domain.entity.Advertisement;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;

public record GetAdvertisementResponseDTO(List<AdvertisementDTO> advertisements) {

    public static GetAdvertisementResponseDTO of(List<Advertisement> advertisements) {
        List<AdvertisementDTO> advertisementDTOs = advertisements.stream()
                .map(ad -> AdvertisementDTO.of(ad.getId(), ad.getPosition(), ad.getImageUrl(), ad.getLinkUrl()))
                .toList();

        return new GetAdvertisementResponseDTO(advertisementDTOs);
    }

    public record AdvertisementDTO(
            Long id,
            Position position,
            String imageUrl,
            String linkUrl
    ) {
        public static AdvertisementDTO of(Long id, Position position, String imageUrl, String linkUrl) {
            return new AdvertisementDTO(id, position, imageUrl, linkUrl);
        }
    }

}
