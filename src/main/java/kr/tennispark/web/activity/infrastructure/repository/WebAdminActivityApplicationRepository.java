package kr.tennispark.web.activity.infrastructure.repository;

import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebAdminActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    @Query("""
            SELECT aa
            FROM ActivityApplication aa
            JOIN FETCH aa.member m
            WHERE aa.activity = :activity
              AND aa.status = true
              AND aa.applicationStatus = :status
              AND m.status = true
            ORDER BY aa.createdAt ASC
            """)
    List<ActivityApplication> findAllByActivityAndStatus(
            @Param("activity") Activity activity,
            @Param("status") ApplicationStatus status
    );
}
