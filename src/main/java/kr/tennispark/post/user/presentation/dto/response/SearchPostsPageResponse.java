package kr.tennispark.post.user.presentation.dto.response;

import java.util.Map;
import kr.tennispark.post.common.domain.entity.Post;
import org.springframework.data.domain.Page;

public record SearchPostsPageResponse(
        Page<PostSearchItemDTO> posts
) {
    public static SearchPostsPageResponse of(Page<Post> page, Map<Long, Long> viewCountMap) {
        Page<PostSearchItemDTO> mapped = page.map(p ->
                PostSearchItemDTO.of(
                        p,
                        viewCountMap.getOrDefault(p.getId(), 0L)
                )
        );
        return new SearchPostsPageResponse(mapped);
    }
}
