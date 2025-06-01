package kr.tennispark.advertisement.admin.infrastructure.repository;

import java.util.List;
import kr.tennispark.advertisement.admin.application.exception.NoSuchAdvertisementException;
import kr.tennispark.advertisement.common.domain.entity.Advertisement;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findAllByPosition(Position position);

    Long countByPosition(Position position);

    default Advertisement getById(Long advertisementId) {
        return findById(advertisementId)
                .orElseThrow(NoSuchAdvertisementException::new);
    }
}
