package kr.tennispark.notification.admin.presentation;

import jakarta.validation.Valid;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.notification.admin.application.NotificationPublisher;
import kr.tennispark.notification.admin.presentation.request.SendMessageRequestDTO;
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

    private final NotificationPublisher publisher;

    @PostMapping("/broadcast")
    public ResponseEntity<ApiResult<?>> sendBroadcastMessage(
            @RequestBody @Valid SendMessageRequestDTO request) {
        publisher.broadcast(request.content());
        return ResponseEntity.ok(ApiUtils.success());
    }
}
