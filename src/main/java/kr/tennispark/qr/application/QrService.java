package kr.tennispark.qr.application;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import kr.tennispark.common.application.S3UploadService;
import kr.tennispark.qr.application.exception.BadQrCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrService {

    private static final String QR_PREFIX = "qr/";
    private static final String QR_SUFFIX = "png";
    private static final Integer QR_SIZE = 200;
    private static final Integer WHITE = 0xFFFFFF;
    private static final Integer BLACK = 0x000000;
    private final S3UploadService s3UploadService;
    private final QrTokenCodec tokenCodec;
    @Value("${qr.url.prefix}")
    private String baseUrl;

    public String generateAndUploadQr(String text) {
        try {
            byte[] qrBytes = generateQrImageBytes(text);
            return s3UploadService.uploadQrImage(qrBytes, QR_PREFIX + LocalDate.now());
        } catch (Exception e) {
            log.error("QR 코드 생성 중 오류 발생: {}", e.getMessage(), e);
            throw new BadQrCreateException();
        }
    }

    // request를 암호화한 qr 생성
    public String createPayloadQr(Object payload, String path) {
        String token = tokenCodec.encode(payload);

        String qrContent = String.format("%s%s?token=%s", baseUrl, path, token);

        return generateAndUploadQr(qrContent);
    }

    private byte[] generateQrImageBytes(String text) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE);

        BufferedImage image = new BufferedImage(QR_SIZE, QR_SIZE, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < QR_SIZE; x++) {
            for (int y = 0; y < QR_SIZE; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, QR_SUFFIX, os);
        return os.toByteArray();
    }

    public <T> T parseToken(String token, Class<T> type) {
        return tokenCodec.decode(token, type);
    }
}
