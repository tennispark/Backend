package kr.tennispark.act.admin.infrastructure.repository;

import kr.tennispark.act.common.domain.Act;
import kr.tennispark.act.common.domain.exception.NoSuchActException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActRepository extends JpaRepository<Act, Long> {

    Page<Act> findAllByOrderByCreatedAtDesc(Pageable pageable);

    default Act getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchActException::new);
    }
}
