package kr.tennispark.point.user.presentation.controller;

import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.point.user.application.service.UserProductService;
import kr.tennispark.point.user.presentation.dto.response.GetAllProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/points/products")
@RequiredArgsConstructor
public class UserProductController {

    private final UserProductService productService;

    @GetMapping
    public ResponseEntity<ApiResult<GetAllProductResponse>> getAllProducts(){
        GetAllProductResponse response = productService.getAllProducts();
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
