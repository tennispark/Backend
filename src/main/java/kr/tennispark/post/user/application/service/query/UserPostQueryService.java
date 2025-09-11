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
import lombok.RequiredArgsConstructor;
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

    public Slice<PostHomeItemResponse> getHome(Member loginMember, Pageable pageable) {
        Slice<Post> slice = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return toHomeSlice(slice, toMemberId(loginMember), pageable);
    }

    public Slice<PostHomeItemResponse> search(Member member, String keyword, Pageable pageable) {
        String q = keyword == null ? "" : keyword.trim();
        if (q.length() < 2) {
            throw new InvalidSearchKeywordException();
        }
        Slice<Post> slice = postRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(q, q, pageable);
        return toHomeSlice(slice, toMemberId(member), pageable);
    }

    private Slice<PostHomeItemResponse> toHomeSlice(Slice<Post> slice, Long loginMemberId, Pageable pageable) {
        List<Post> posts = slice.getContent();
        if (posts.isEmpty()) {
            return new SliceImpl<>(List.of(), pageable, slice.hasNext());
        }

        List<Long> postIds = posts.stream().map(Post::getId).toList();

        Map<Long, Long> viewMap = loadViewCounts(postIds);
        Set<Long> likedIds = loadLikedIds(loginMemberId, postIds);

        List<PostHomeItemResponse> items = assembleItems(posts, viewMap, likedIds, loginMemberId);

        return new SliceImpl<>(items, pageable, slice.hasNext());
    }

    private Long toMemberId(Member member) {
        return (member == null) ? null : member.getId();
    }

    private Map<Long, Long> loadViewCounts(List<Long> postIds) {
        return postIds.isEmpty() ? Map.of() : viewCountService.getBulk(postIds);
    }

    private Set<Long> loadLikedIds(Long loginMemberId, List<Long> postIds) {
        if (loginMemberId == null || postIds.isEmpty()) {
            return Set.of();
        }
        return new HashSet<>(likeRepository.findLikedPostIds(loginMemberId, postIds));
    }

    private List<PostHomeItemResponse> assembleItems(List<Post> posts,
                                                     Map<Long, Long> viewMap,
                                                     Set<Long> likedIds,
                                                     Long loginMemberId) {
        return posts.stream()
                .map(p -> PostHomeItemResponse.of(
                        p,
                        viewMap.getOrDefault(p.getId(), 0L),
                        loginMemberId != null && likedIds.contains(p.getId()),
                        loginMemberId != null
                                && p.getMember() != null
                                && loginMemberId.equals(p.getMember().getId())
                ))
                .toList();
    }

}