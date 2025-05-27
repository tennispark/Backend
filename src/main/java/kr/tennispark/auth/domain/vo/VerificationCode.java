package kr.tennispark.auth.domain.vo;

import java.security.SecureRandom;

public class VerificationCode {

    private static final SecureRandom secureRandom = new SecureRandom();

    private final String value;
    
    public VerificationCode(String value) {
        if (!value.matches("\\d{6}")) {
            throw new IllegalArgumentException("6자리 숫자만 허용됩니다.");
        }
        this.value = value;
    }

    public static String generateCode() {
        int code = secureRandom.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public String getValue() {
        return value;
    }
}
