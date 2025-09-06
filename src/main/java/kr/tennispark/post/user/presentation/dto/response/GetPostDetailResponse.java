package kr.tennispark.post.user.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.post.common.domain.entity.Post;

public record GetPostDetailResponse(
        Long id,
        String authorName,
        LocalDateTime createdAt,
        String title,
        String content,
        List<String> photos,
        int likeCount,
        int commentCount,
        long viewCount,
        Boolean likedByMe,
        Boolean authoredByMe,
        Boolean notificationEnabled
) {
    public static GetPostDetailResponse of(Post post,
                                           long viewCount,
                                           boolean likedByMe,
                                           boolean authoredByMe) {
        return new GetPostDetailResponse(
                post.getId(),
                post.getMember().getName(),
                post.getCreatedAt(),
                post.getTitle(),
                post.getContent(),
                post.getPhotos() != null ? post.getPhotos().toList() : List.of(),
                post.getLikeCount(),
                post.getCommentCount(),
                viewCount,
                likedByMe,
                authoredByMe,
                authoredByMe ? post.isNotificationEnabled() : null
        );
    }
}

