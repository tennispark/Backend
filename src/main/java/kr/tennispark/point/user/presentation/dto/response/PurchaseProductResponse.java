package kr.tennispark.point.user.presentation.dto.response;

import kr.tennispark.point.common.domain.entity.Product;

public record PurchaseProductResponse(
        String qrCodeUrl,
        Long productId,
        String productName,
        String brand,
        Integer points
) {
    public static PurchaseProductResponse of(String qrUrl, Product p) {
        return new PurchaseProductResponse(
                qrUrl, p.getId(), p.getName(), p.getBrand(), p.getPoint()
        );
    }
}

