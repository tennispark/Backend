package kr.tennispark.post.user.infrastructure.repository;

import kr.tennispark.post.common.domain.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, Long> {}
