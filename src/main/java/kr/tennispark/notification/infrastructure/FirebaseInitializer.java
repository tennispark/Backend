package kr.tennispark.notification.infrastructure;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
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
                throw new RuntimeException("Failed to initialize FirebaseApp");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize FirebaseApp", e);
        }
    }
}
