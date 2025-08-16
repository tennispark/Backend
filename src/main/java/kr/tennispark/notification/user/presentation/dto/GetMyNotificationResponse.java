package kr.tennispark.notification.user.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.notification.common.domain.entity.Notification;

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
            LocalDateTime date
    ) {
        public static NotificationItemDTO of(Notification n) {
            return new NotificationItemDTO(
                    n.getCategory().name(),
                    n.getContent(),
                    n.getCreatedAt()
            );
        }
    }
}
