package kr.tennispark.activity.admin.infrastructure.repository;

import kr.tennispark.activity.common.domain.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityImageRepository extends JpaRepository<ActivityImage, Long> {
}
