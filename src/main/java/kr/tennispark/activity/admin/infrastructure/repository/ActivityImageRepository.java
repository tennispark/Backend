package kr.tennispark.activity.admin.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.activity.common.domain.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, Long> {

    List<ActivityImage> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
