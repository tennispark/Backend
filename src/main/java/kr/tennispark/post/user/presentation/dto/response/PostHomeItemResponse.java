package kr.tennispark.post.user.presentation.dto.response;

import java.time.LocalDateTime;
import kr.tennispark.post.common.domain.entity.Post;

public record PostHomeItemResponse(
        Long id,
        String authorName,
        LocalDateTime createdAt,
        String title,
        String content,
        String mainImage,
        int likeCount,
        int commentCount,
        long viewCount,
        boolean likedByMe,
        boolean authoredByMe
) {
    public static PostHomeItemResponse of(Post post,
                                          long viewCount,
                                          boolean likedByMe,
                                          boolean authoredByMe) {
        String mainPhoto = null;
        if (post.getPhotos() != null) {
            mainPhoto =
                    post.getPhotos().getMainImage();
        }
        return new PostHomeItemResponse(
                post.getId(),
                post.getMember().getName(),
                post.getCreatedAt(),
                post.getTitle(),
                post.getContent(),
                mainPhoto,
                post.getLikeCount(),
                post.getCommentCount(),
                viewCount,
                likedByMe,
                authoredByMe
        );
    }
}
