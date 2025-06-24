package kr.tennispark.notification.infrastructure;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import kr.tennispark.notification.infrastructure.exception.FirebaseInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FirebaseInitializer {

    @Value("${fcm.file_path}")
    private String FILE_PATH;

    @PostConstruct
    public void initialize() {
        try (InputStream serviceAccount =
                     getClass().getClassLoader().getResourceAsStream(FILE_PATH)) {

            if (serviceAccount == null) {
                throw new FirebaseInitializationException("FCM 서비스 계정 파일을 찾을 수 없습니다: " + FILE_PATH);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new FirebaseInitializationException("FirebaseApp 초기화 중 오류 발생");
        }
    }
}
