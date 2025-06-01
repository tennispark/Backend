package kr.tennispark.point.common.infrastructure;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.tennispark.point.common.domain.entity.Product;
import kr.tennispark.point.common.domain.exception.NoSuchProductException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id and p.status = true")
    Optional<Product> findWithPessimisticLockById(@Param("id") Long id);

    default Product getById(Long id) {
        return findWithPessimisticLockById(id)
                .orElseThrow(NoSuchProductException::new);
    }
}