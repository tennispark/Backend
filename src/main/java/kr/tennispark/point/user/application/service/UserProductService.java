package kr.tennispark.point.user.application.service;

import java.util.List;
import kr.tennispark.point.common.domain.entity.Product;
import kr.tennispark.point.user.infrastrurcture.repository.UserProductRepository;
import kr.tennispark.point.user.presentation.dto.response.GetAllProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProductService {

    private final UserProductRepository productRepository;

    public GetAllProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        return GetAllProductResponse.of(products);
    }
}
