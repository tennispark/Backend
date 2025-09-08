package kr.tennispark.post.user.application.service.query;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.common.domain.entity.Post;
import kr.tennispark.post.common.domain.entity.PostLike;
import kr.tennispark.post.user.application.exception.InvalidSearchKeywordException;
import kr.tennispark.post.user.application.service.redis.PostViewCountService;
import kr.tennispark.post.user.infrastructure.repository.UserPostLikeRepository;
import kr.tennispark.post.user.infrastructure.repository.UserPostRepository;
import kr.tennispark.post.user.presentation.dto.response.GetPostDetailResponse;
import kr.tennispark.post.user.presentation.dto.response.PostHomeItemResponse;
import kr.tennispark.post.user.presentation.dto.response.SearchPostsPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    @Transactional(readOnly = true)
    public Slice<PostHomeItemResponse> getHome(Member loginMember, Pageable pageable) {
        Slice<Post> slice = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<Post> posts = slice.getContent();

        List<Long> postIds = posts.stream().map(Post::getId).toList();
        Long loginMemberId = (loginMember == null) ? null : loginMember.getId();

        Map<Long, Long> viewMap = postIds.isEmpty() ? Map.of() : viewCountService.getBulk(postIds);
        Set<Long> likedIds = (loginMemberId == null || postIds.isEmpty())
                ? Set.of() : new HashSet<>(likeRepository.findLikedPostIds(loginMemberId, postIds));

        List<PostHomeItemResponse> items = posts.stream()
                .map(p -> PostHomeItemResponse.of(
                        p,
                        viewMap.getOrDefault(p.getId(), 0L),
                        loginMemberId != null && likedIds.contains(p.getId()),
                        loginMemberId != null && p.getMember() != null && loginMemberId.equals(p.getMember().getId())
                ))
                .toList();

        return new SliceImpl<>(items, pageable, slice.hasNext());
    }

    public SearchPostsPageResponse search(Member loginMember, String keyword, Pageable pageable) {
        String q = keyword == null ? "" : keyword.trim();
        if (q.length() < 2) {
            throw new InvalidSearchKeywordException();
        }

        Page<Post> page = postRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(q, q, pageable);

        List<Long> ids = page.getContent().stream().map(Post::getId).toList();
        Map<Long, Long> viewMap = ids.isEmpty() ? Map.of() : viewCountService.getBulk(ids);

        return SearchPostsPageResponse.of(page, viewMap);
    }

}