package kr.tennispark.notification.application;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.stream.Collectors;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.notification.application.exception.FcmMessageSendFailureException;
import kr.tennispark.notification.presentation.request.SendMessageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmMessageService {

    private static final int BATCH_SIZE = 500;
    private final MemberRepository memberRepository;

    public void sendBroadcastMessage(SendMessageRequestDTO request) {
        List<String> fcmTokens = getValidFcmTokens();

        // 500개씩 나눠서 전송
        for (int i = 0; i < fcmTokens.size(); i += BATCH_SIZE) {
            List<String> batch = fcmTokens.subList(i, Math.min(i + BATCH_SIZE, fcmTokens.size()));
            sendMulticast(request, batch);
        }
    }

    private List<String> getValidFcmTokens() {
        return memberRepository.findAll().stream()
                .map(Member::getFcmToken)
                .filter(token -> !StringUtils.isBlank(token))
                .distinct()
                .collect(Collectors.toList());
    }

    private void sendMulticast(SendMessageRequestDTO request, List<String> tokens) {
        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(request.title())
                        .setBody(request.content())
                        .build())
                .addAllTokens(tokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

            if (response.getFailureCount() > 0) {
                List<SendResponse> failedResponses = response.getResponses().stream()
                        .filter(r -> !r.isSuccessful())
                        .toList();

                for (int i = 0; i < failedResponses.size(); i++) {
                    String failedToken = tokens.get(i);
                    String error = failedResponses.get(i).getException().getMessage();
                    log.warn("FCM 실패 - token: {}, error: {}", failedToken, error);
                }
            }
        } catch (FirebaseMessagingException e) {
            log.error("FCM 멀티캐스트 전송 실패", e);
            throw new FcmMessageSendFailureException();
        }
    }
}