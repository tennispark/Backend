package kr.tennispark.post.user.presentation.dto.response;

import java.util.List;

public record SliceResponse<T>(
        List<T> content,
        int page,
        int size,
        boolean first,
        boolean last,
        boolean hasNext,
        int numberOfElements
) {
    public static <T> SliceResponse<T> of(org.springframework.data.domain.Slice<T> s) {
        return new SliceResponse<>(
                s.getContent(),
                s.getNumber(),
                s.getSize(),
                s.isFirst(),
                s.isLast(),
                s.hasNext(),
                s.getNumberOfElements()
        );
    }
}