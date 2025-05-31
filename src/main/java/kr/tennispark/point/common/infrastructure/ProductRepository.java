package kr.tennispark.point.common.infrastructure;

import kr.tennispark.point.common.domain.entity.Product;
import kr.tennispark.point.common.domain.exception.NoSuchProductException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchProductException::new);
    }
}