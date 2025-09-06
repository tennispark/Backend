package kr.tennispark.post.user.application.service.query;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.common.domain.entity.Post;
import kr.tennispark.post.common.domain.entity.PostLike;
import kr.tennispark.post.user.application.service.redis.PostViewCountService;
import kr.tennispark.post.user.infrastructure.repository.UserPostLikeRepository;
import kr.tennispark.post.user.infrastructure.repository.UserPostRepository;
import kr.tennispark.post.user.presentation.dto.response.GetPostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPostQueryService {

    private final UserPostRepository postRepository;
    private final UserPostLikeRepository likeRepository;
    private final PostViewCountService viewCountService;

    public GetPostDetailResponse getPostDetail(Member loginMember, Long postId) {
        Post post = postRepository.getById(postId);

        long viewCount = viewCountService.incrementAndGet(postId);

        boolean authoredByMe = post.getMember().getId().equals(loginMember.getId());

        boolean likedByMe = false;
        likedByMe = likeRepository.findByPostIdAndMemberId(postId, loginMember.getId())
                .map(PostLike::isLiked)
                .orElse(false);

        return GetPostDetailResponse.of(post, viewCount, likedByMe, authoredByMe);
    }
}