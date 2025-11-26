package kr.tennispark.web.activity.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.enums.ActivityType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebAdminActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * 기존 API — 특정 날짜 이후 조회 (이번 주)
     */
    @Query("""
            SELECT a
            FROM Activity a
            WHERE a.date >= :fromDate
              AND a.type = :type
              AND a.status = true
            ORDER BY a.date ASC, a.scheduledTime.beginAt ASC, a.id ASC
            """)
    List<Activity> findFromDate(
            @Param("fromDate") LocalDate fromDate,
            @Param("type") ActivityType type
    );

    /**
     * 신규 기능 — 날짜 범위 조회
     */
    @Query("""
            SELECT a
            FROM Activity a
            WHERE (:fromDate IS NULL OR a.date >= :fromDate)
              AND (:toDate IS NULL OR a.date <= :toDate)
              AND a.type = :type
              AND a.status = true
            ORDER BY a.date ASC, a.scheduledTime.beginAt ASC, a.id ASC
            """)
    List<Activity> findActivities(
            @Param("type") ActivityType type,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}
