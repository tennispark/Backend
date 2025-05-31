package kr.tennispark.point.admin.presentation.controller;

import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.point.admin.application.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/points/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService productService;

    @PostMapping("/purchases")
    public ResponseEntity<ApiResult<String>> purchaseProduct(@RequestParam(name = "token") String token) {
        productService.purchaseProduct(token);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
