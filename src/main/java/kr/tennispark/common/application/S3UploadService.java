package kr.tennispark.common.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kr.tennispark.common.application.exception.S3FailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploadService {

    private static final String DEFAULT_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_EXTENSION = ".png";
    private static final String SLASH = "/";
    private static final int KEY_START_INDEX = 1;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImageFile(MultipartFile image, String keyPrefix) {
        try {
            String contentType = image.getContentType();
            String originalFilename = image.getOriginalFilename();
            String extension = extractExtension(originalFilename);

            return uploadToS3(
                    new ByteArrayInputStream(image.getBytes()),
                    image.getSize(),
                    keyPrefix,
                    contentType,
                    extension
            );
        } catch (IOException e) {
            throw new S3FailedException("이미지 업로드 실패");
        }
    }

    public String uploadQrImage(byte[] imageData, String keyPrefix) {
        return uploadToS3(new ByteArrayInputStream(imageData), imageData.length, keyPrefix, DEFAULT_CONTENT_TYPE,
                DEFAULT_EXTENSION);
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

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return DEFAULT_EXTENSION;
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    public void deleteFiles(Collection<String> fileUrls) {
        if (fileUrls == null || fileUrls.isEmpty()) {
            return;
        }

        List<KeyVersion> keys = fileUrls.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .map(url -> {
                    try {
                        return new DeleteObjectsRequest.KeyVersion(extractKeyFromUrl(url));
                    } catch (S3FailedException e) {
                        log.warn("잘못된 S3 URL로 인해 삭제 대상에서 제외됨: {}", url, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        if (keys.isEmpty()) {
            return;
        }

        DeleteObjectsRequest request = new DeleteObjectsRequest(bucket).withKeys(keys);
        amazonS3.deleteObjects(request);
    }

    private String extractKeyFromUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            if (path == null || path.length() <= KEY_START_INDEX) {
                throw new S3FailedException("Invalid S3 URL: " + url);
            }
            return path.substring(KEY_START_INDEX);
        } catch (URISyntaxException e) {
            throw new S3FailedException("Invalid S3 URL: " + url + "\n" + e);
        }
    }
}
