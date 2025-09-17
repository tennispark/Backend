package kr.tennispark.post.user.presentation.dto.response;

public record GetNotificationEnabledResponse(boolean notificationEnabled) {
    public static GetNotificationEnabledResponse of(boolean enabled) {
        return new GetNotificationEnabledResponse(enabled);
    }
}
