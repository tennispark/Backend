package kr.tennispark.point.user.infrastrurcture.repository;

import kr.tennispark.point.common.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends JpaRepository<Product, Long> {
}