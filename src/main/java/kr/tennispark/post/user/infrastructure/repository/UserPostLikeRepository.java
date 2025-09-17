package kr.tennispark.post.user.infrastructure.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import kr.tennispark.post.common.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostIdAndMemberId(Long postId, Long memberId);

    @Query("select l.post.id from PostLike l " +
            "where l.member.id = :memberId and l.liked = true and l.post.id in :postIds and l.status = true")
    List<Long> findLikedPostIds(Long memberId, Collection<Long> postIds);
}
