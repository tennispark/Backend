package kr.tennispark.activity.user.presentation.dto.response;

import java.util.List;

public record GetActivityImageResponse(
        int totalCount,
        List<String> images
) {

    public static GetActivityImageResponse of(List<String> images) {
        return new GetActivityImageResponse(images.size(), images);
    }
}
