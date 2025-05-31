package kr.tennispark.activity.user.presentation.dto.request;

public record PurchasePayload(
        long productId,
        long memberId
) {
}
