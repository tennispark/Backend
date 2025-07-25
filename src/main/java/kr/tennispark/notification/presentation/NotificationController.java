package kr.tennispark.notification.presentation;

import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.notification.application.FcmMessageService;
import kr.tennispark.notification.presentation.request.SendMessageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final FcmMessageService messageService;

    @PostMapping("/broadcast")
    public ResponseEntity<ApiResult<?>> sendBroadcastMessage(
            @RequestBody SendMessageRequestDTO request) {
        messageService.sendBroadcastMessage(request.content());
        return ResponseEntity.ok(ApiUtils.success());
    }
}
