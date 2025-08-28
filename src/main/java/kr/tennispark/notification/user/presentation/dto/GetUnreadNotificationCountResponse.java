package kr.tennispark.notification.user.presentation.dto;

public record GetUnreadNotificationCountResponse(long unreadCount) {

    public static GetUnreadNotificationCountResponse of(long unreadCount) {
        return new GetUnreadNotificationCountResponse(unreadCount);
    }
}
