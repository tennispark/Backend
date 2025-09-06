package kr.tennispark.post.user.application.service;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.common.domain.entity.Post;
import kr.tennispark.post.common.domain.entity.PostLike;
import kr.tennispark.post.user.infrastructure.repository.UserPostLikeRepository;
import kr.tennispark.post.user.infrastructure.repository.UserPostRepository;
import kr.tennispark.post.user.presentation.dto.response.CreatePostLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPostLikeCommandService {

    private final UserPostRepository postRepository;
    private final UserPostLikeRepository likeRepository;

    public CreatePostLikeResponse createPostLike(Member member, Long postId) {
        Post post = postRepository.getById(postId);

        PostLike like = likeRepository.findByPostIdAndMemberId(postId, member.getId())
                .orElse(PostLike.create(post, member));
        boolean nowLiked = like.toggleLike();

        likeRepository.save(like);
        return new CreatePostLikeResponse(nowLiked, post.getLikeCount());
    }
}