package kr.tennispark.qr.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private static final String IMAGE_TYPE = "image/png";
    private static final String FILE_EXTENSION = ".png";
    private static final String SLASH = "/";


    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadQrImage(byte[] imageData, String keyPrefix) {
        String fileName = keyPrefix + SLASH + UUID.randomUUID() + FILE_EXTENSION;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(IMAGE_TYPE);
        metadata.setContentLength(imageData.length);

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, new ByteArrayInputStream(imageData), metadata));
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}