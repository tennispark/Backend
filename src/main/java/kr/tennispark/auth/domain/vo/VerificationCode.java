package kr.tennispark.auth.domain.vo;

public class VerificationCode {

    private final String value;

    public VerificationCode(String value) {
        if (!value.matches("\\d{6}")) {
            throw new IllegalArgumentException("6자리 숫자만 허용됩니다.");
        }
        this.value = value;
    }

    public static VerificationCode generate() {
        String code = String.valueOf((int) (Math.random() * 900000) + 100000);
        return new VerificationCode(code);
    }

    public String getValue() {
        return value;
    }
}
