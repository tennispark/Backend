package kr.tennispark.post.user.infrastructure.repository;

import kr.tennispark.post.common.domain.entity.Post;
import kr.tennispark.post.common.domain.exception.NoSuchPostException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<Post, Long> {

    default Post getById(Long postId) {
        return findById(postId)
                .orElseThrow(NoSuchPostException::new);
    }

    @EntityGraph(attributePaths = "member")
    Slice<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = "member")
    Slice<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(
            String titleKeyword,
            String contentKeyword,
            Pageable pageable
    );
}
