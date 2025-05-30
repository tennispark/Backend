package kr.tennispark.qr.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private static final String DEFAULT_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_EXTENSION = ".png";
    private static final String SLASH = "/";

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadQrImage(byte[] imageData, String keyPrefix) {
        return uploadToS3(new ByteArrayInputStream(imageData), imageData.length, keyPrefix, DEFAULT_CONTENT_TYPE,
                DEFAULT_EXTENSION);
    }

    public String uploadAdvertisementImage(MultipartFile file, String keyPrefix) {
        try {
            return uploadToS3(file.getInputStream(), file.getSize(), keyPrefix, file.getContentType(),
                    DEFAULT_EXTENSION);
        } catch (IOException e) {
            throw new UncheckedIOException("광고 배너 이미지 업로드에 실패하였습니다.", e);
        }
    }

    private String uploadToS3(InputStream inputStream, long contentLength, String keyPrefix, String contentType,
                              String extension) {
        String fileName = keyPrefix + SLASH + UUID.randomUUID() + extension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(contentLength);

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}
