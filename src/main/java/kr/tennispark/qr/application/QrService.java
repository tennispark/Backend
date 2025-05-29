package kr.tennispark.qr.application;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import kr.tennispark.qr.application.exception.BadQrCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrService {

    private static final String QR_PREFIX = "qr/";
    private static final String QR_SUFFIX = "png";
    private static final Integer QR_SIZE = 200;

    private final S3UploadService s3UploadService;

    public String generateAndUploadQr(String text) {
        try {
            byte[] qrBytes = generateQrImageBytes(text);
            return s3UploadService.uploadQrImage(qrBytes, QR_PREFIX + LocalDate.now());
        } catch (Exception e) {
            throw new BadQrCreateException();
        }
    }

    private byte[] generateQrImageBytes(String text) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE);

        BufferedImage image = new BufferedImage(QR_SIZE, QR_SIZE, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < QR_SIZE; x++) {
            for (int y = 0; y < QR_SIZE; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, QR_SUFFIX, os);
        return os.toByteArray();
    }
}
