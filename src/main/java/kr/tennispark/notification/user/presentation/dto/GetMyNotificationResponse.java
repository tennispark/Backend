package kr.tennispark.notification.user.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.notification.common.domain.entity.Notification;
import kr.tennispark.post.common.domain.entity.Post;

public record GetMyNotificationResponse(
        List<NotificationItemDTO> notifications
) {

    public static GetMyNotificationResponse of(List<Notification> notifications) {
        return new GetMyNotificationResponse(
                notifications.stream()
                        .map(NotificationItemDTO::of)
                        .toList()
        );
    }

    public record NotificationItemDTO(
            String type,
            String content,
            LocalDateTime date,
            @JsonInclude(JsonInclude.Include.NON_NULL) Long postId
    ) {
        public static NotificationItemDTO of(Notification n) {
            Post post = n.getPost();
            return new NotificationItemDTO(
                    n.getCategory().name(),
                    n.getContent(),
                    n.getCreatedAt(),
                    post != null ? n.getPost().getId() : null
            );
        }
    }
}
