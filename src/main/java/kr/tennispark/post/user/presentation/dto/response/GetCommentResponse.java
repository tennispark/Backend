package kr.tennispark.post.user.presentation.dto.response;


import java.util.List;

public record GetCommentResponse(
        List<CommentItemDTO> comments
) {
    public static GetCommentResponse of(List<CommentItemDTO> comments) {
        return new GetCommentResponse(comments == null ? List.of() : List.copyOf(comments));
    }
}
