package kr.tennispark.qr.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.tennispark.qr.application.exception.FailToCreateQRPayloadException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QrTokenCodec {

    private final ObjectMapper mapper = new ObjectMapper();
    private final TextEncryptor encryptor;

    public String encode(Object payload) {
        try {
            String json = mapper.writeValueAsString(payload);
            return encryptor.encrypt(json);
        } catch (JsonProcessingException e) {
            throw new FailToCreateQRPayloadException();
        }
    }

    public <T> T decode(String token, Class<T> type) {
        try {
            String json = encryptor.decrypt(token);
            return mapper.readValue(json, type);
        } catch (Exception ex) {
            throw new FailToCreateQRPayloadException();
        }
    }
}

