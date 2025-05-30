package kr.tennispark.members.user.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.exception.NoSuchMemberException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhone_Number(String number);

    List<Member> findByNameContaining(String name);

    default Member getById(Long id) {
        return findById(id)
                .orElseThrow(NoSuchMemberException::new);
    }

    Optional<Member> findByPhone_Number(String number);
}
