package kr.tennispark.point.user.presentation.dto.response;

import java.util.List;
import kr.tennispark.point.common.domain.entity.Product;

public record GetAllProductResponse(
        List<ProductDto> products
) {

    public static GetAllProductResponse of(List<Product> products) {
        List<ProductDto> dtoList = products.stream()
                .map(ProductDto::of)
                .toList();
        return new GetAllProductResponse(dtoList);
    }
}
