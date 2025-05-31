package kr.tennispark.point.user.presentation.controller;

import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.user.application.service.UserProductService;
import kr.tennispark.point.user.presentation.dto.response.GetAllProductResponse;
import kr.tennispark.point.user.presentation.dto.response.PurchaseProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/points/products")
@RequiredArgsConstructor
public class UserProductController {

    private final UserProductService productService;

    @GetMapping
    public ResponseEntity<ApiResult<GetAllProductResponse>> getAllProducts() {
        GetAllProductResponse response = productService.getAllProducts();
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/{productId}/purchases/qr")
    public ResponseEntity<ApiResult<PurchaseProductResponse>> issue(
            @LoginMember Member member,
            @PathVariable("productId") Long productId) {
        PurchaseProductResponse response = productService.createPurchaseQr(productId, member);
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
