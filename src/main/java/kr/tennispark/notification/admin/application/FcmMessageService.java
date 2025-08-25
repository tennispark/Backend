package kr.tennispark.notification.admin.application;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import java.util.List;
import kr.tennispark.notification.admin.application.exception.FcmMessageSendFailureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmMessageService {

    private static final int BATCH_SIZE = 500;
    private static final String TITLE = "Tennis Park";

    public void sendMessage(List<String> tokens, String content) {
        for (int i = 0; i < tokens.size(); i += BATCH_SIZE) {
            List<String> batch = tokens.subList(i, Math.min(i + BATCH_SIZE, tokens.size()));
            sendMulticast(content, batch);
        }
    }

    private void sendMulticast(String content, List<String> tokens) {
        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(TITLE)
                        .setBody(content)
                        .build())
                .addAllTokens(tokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

            for (int i = 0; i < response.getResponses().size(); i++) {
                SendResponse sendResponse = response.getResponses().get(i);
                if (!sendResponse.isSuccessful()) {
                    String failedToken = tokens.get(i);
                    String error = sendResponse.getException().getMessage();
                    log.warn("FCM 실패 - token: {}, error: {}", failedToken, error);
                }
            }
        } catch (FirebaseMessagingException e) {
            log.error("FCM 멀티캐스트 전송 실패", e);
            throw new FcmMessageSendFailureException();
        }
    }
}