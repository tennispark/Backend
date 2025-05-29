package kr.tennispark.common.qr;

import java.time.LocalDate;
import kr.tennispark.common.qr.exception.BadQrCreateException;
import kr.tennispark.common.qr.image.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrService {

    private static final String QR_PREFIX = "qr/";

    private final QrCodeUtil qrCodeUtil;
    private final S3UploadService s3UploadService;

    public String generateAndUploadQr(String text) {
        try {
            byte[] qrBytes = qrCodeUtil.generateQrImageBytes(text, 200, 200);
            return s3UploadService.uploadQrImage(qrBytes, QR_PREFIX + LocalDate.now());
        } catch (Exception e) {
            throw new BadQrCreateException();
        }
    }
}