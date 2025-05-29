package kr.tennispark.record.admin.infrastructure;

import kr.tennispark.record.common.domain.entity.association.MemberRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRecordRepository extends JpaRepository<MemberRecord, Long> {
}
