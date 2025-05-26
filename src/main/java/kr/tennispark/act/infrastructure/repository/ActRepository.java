package kr.tennispark.act.infrastructure.repository;

import kr.tennispark.act.domain.Act;
import kr.tennispark.act.domain.exception.NoSuchActException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActRepository extends JpaRepository<Act, Long> {

    default Act getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchActException::new);
    }
}
