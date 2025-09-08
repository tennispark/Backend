package kr.tennispark.post.user.presentation.dto.response;


import java.time.LocalDateTime;
import kr.tennispark.post.common.domain.entity.Post;

public record PostSearchItemDTO(
        String authorName,
        LocalDateTime createdAt,
        String title,
        String content,
        long viewCount,
        int commentCount
) {
    public static PostSearchItemDTO of(Post post, long viewCount) {
        return new PostSearchItemDTO(
                post.getMember().getName(),
                post.getCreatedAt(),
                post.getTitle(),
                post.getContent(),
                viewCount,
                post.getCommentCount() == null ? 0 : post.getCommentCount()
        );
    }
}