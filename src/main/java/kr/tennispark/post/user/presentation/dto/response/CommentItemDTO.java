package kr.tennispark.post.user.presentation.dto.response;

import java.time.LocalDateTime;
import kr.tennispark.post.common.domain.entity.Comment;

public record CommentItemDTO(
        Long id,
        String authorName,
        String content,
        String photoUrl,
        LocalDateTime createdAt,
        boolean authoredByMe
) {
    public static CommentItemDTO of(Comment c, Long loginMemberId) {
        boolean authored = loginMemberId != null
                && c.getMember() != null
                && loginMemberId.equals(c.getMember().getId());
        return new CommentItemDTO(
                c.getId(),
                c.getMember().getName(),
                c.getContent(),
                c.getPhotoUrl(),
                c.getCreatedAt(),
                authored
        );
    }
}

