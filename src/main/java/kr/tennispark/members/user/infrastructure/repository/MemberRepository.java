package kr.tennispark.members.user.infrastructure.repository;

import java.util.Optional;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.exception.NoSuchMemberException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByPhone_Number(String number);

    default Member getMemberByPhone_Number(String number) {
        return findByPhone_Number(number)
                .orElseThrow(NoSuchMemberException::new);
    }
}
