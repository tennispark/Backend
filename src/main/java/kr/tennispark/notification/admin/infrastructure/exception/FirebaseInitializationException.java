package kr.tennispark.notification.admin.infrastructure.exception;

public class FirebaseInitializationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Firebase 초기화에 실패하였습니다.";

    public FirebaseInitializationException(final String message) {
        super(message);
    }
}
