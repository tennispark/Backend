package kr.tennispark.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class CryptoConfig {

    @Bean
    public TextEncryptor textEncryptor(
            @Value("${qr.password}") String password,
            @Value("${qr.salt}") String saltHex
    ) {
        return Encryptors.text(password, saltHex);
    }
}

