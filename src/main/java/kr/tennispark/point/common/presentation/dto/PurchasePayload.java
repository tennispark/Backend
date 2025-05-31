package kr.tennispark.point.common.presentation.dto;

public record PurchasePayload(
        long productId,
        long memberId
) {
}
