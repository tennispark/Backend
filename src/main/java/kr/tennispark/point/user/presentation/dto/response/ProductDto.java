package kr.tennispark.point.user.presentation.dto.response;


import kr.tennispark.point.common.domain.entity.Product;

public record ProductDto(
        Long productId,
        String name,
        String brand,
        Integer price,
        String imageUrl
) {

    public static ProductDto of(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPoint(),
                product.getImageUrl()
        );
    }
}
