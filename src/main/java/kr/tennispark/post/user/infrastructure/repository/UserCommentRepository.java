package kr.tennispark.post.user.infrastructure.repository;

import java.util.List;
import kr.tennispark.post.common.domain.entity.Comment;
import kr.tennispark.post.common.domain.exception.NoSuchCommentException;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommentRepository extends JpaRepository<Comment, Long> {

    default Comment getById(Long id) {
        return findById(id).orElseThrow(NoSuchCommentException::new);
    }

    @EntityGraph(attributePaths = "member")
    List<Comment> findAllByPostIdOrderByCreatedAtAsc(Long postId);
}
