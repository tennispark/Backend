package kr.tennispark.notification.application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
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

    private final MemberRepository memberRepository;

    public void sendBroadcastMessage(SendMessageRequestDTO request) {
        List<String> fcmTokens = getValidFcmTokens();

        for (String token : fcmTokens) {
            sendToToken(request, token);
        }
    }

    private List<String> getValidFcmTokens() {
        return memberRepository.findAll().stream()
                .map(Member::getFcmToken)
                .filter(token -> !StringUtils.isBlank(token))
                .distinct()
                .collect(Collectors.toList());
    }

    private void sendToToken(SendMessageRequestDTO request, String token) {
        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(request.title())
                        .setBody(request.content())
                        .build())
                .setToken(token)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.warn("Failed to send FCM message to token: {} | reason: {}", token, e.getMessage());
            throw new FcmMessageSendFailureException();
        }
    }
}
