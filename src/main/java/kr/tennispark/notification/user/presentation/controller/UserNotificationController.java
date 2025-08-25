package kr.tennispark.notification.user.presentation.controller;

import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.notification.user.application.service.UserNotificationQueryService;
import kr.tennispark.notification.user.presentation.dto.GetMyNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/notifications")
@RequiredArgsConstructor
public class UserNotificationController {

    private final UserNotificationQueryService notificationQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiResult<GetMyNotificationResponse>> getMyNotifications(
            @LoginMember Member member) {
        GetMyNotificationResponse response = notificationQueryService.getMyNotifications(member);
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
