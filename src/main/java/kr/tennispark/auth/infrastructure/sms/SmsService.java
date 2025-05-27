package kr.tennispark.auth.infrastructure.sms;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SmsService {

    private static final String AUTH_MESSAGE_TEMPLATE =
            "[테니스파크] 인증번호는 %s 입니다. 정확히 입력해주세요.";
    private final SmsProperties smsProperties;
    private DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(
                smsProperties.apiKey(),
                smsProperties.apiSecret(),
                smsProperties.apiUrl()
        );
    }

    public SingleMessageSentResponse sendSms(String to, String text) {
        Message message = new Message();
        message.setFrom(smsProperties.fromNumber());
        message.setTo(to);
        message.setText(buildAuthMessage(text));

        return this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    private String buildAuthMessage(String value) {
        return String.format(AUTH_MESSAGE_TEMPLATE, value);
    }
}
