package kr.tennispark.auth.infrastructure.sms;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SmsService {

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
}
