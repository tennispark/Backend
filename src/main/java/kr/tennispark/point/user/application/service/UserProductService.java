package kr.tennispark.point.user.application.service;

import java.util.List;
import kr.tennispark.activity.user.presentation.dto.request.PurchasePayload;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.common.domain.entity.Product;
import kr.tennispark.point.user.infrastrurcture.repository.UserProductRepository;
import kr.tennispark.point.user.presentation.dto.response.GetAllProductResponse;
import kr.tennispark.point.user.presentation.dto.response.PurchaseProductResponse;
import kr.tennispark.qr.application.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProductService {

    private final UserProductRepository productRepository;
    private final QrService qrService;

    @Value("${qr.url.product.suffix}")
    private String qrUrlSuffix;

    public GetAllProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        return GetAllProductResponse.of(products);
    }

    public PurchaseProductResponse createPurchaseQr(long productId, Member member) {
        Product product = productRepository.getById(productId);

        PurchasePayload payload = new PurchasePayload(productId, member.getId());

        String qrUrl = qrService.createPayloadQr(payload, qrUrlSuffix);

        return PurchaseProductResponse.of(qrUrl, product);
    }
}
