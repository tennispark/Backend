package kr.tennispark.web.member.presentation.controller;

import jakarta.validation.Valid;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.web.member.application.service.WebMemberManagementCommandService;
import kr.tennispark.web.member.application.service.WebMemberManagementQueryService;
import kr.tennispark.web.member.presentation.dto.GetMemberManagementResponse;
import kr.tennispark.web.member.presentation.dto.UpdateCouponRequest;
import kr.tennispark.web.member.presentation.dto.UpdateMemberTypeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class WebMemberManagementController {

    private final WebMemberManagementQueryService memberQueryService;
    private final WebMemberManagementCommandService commandService;

    @GetMapping("/management")
    public ResponseEntity<ApiResult<GetMemberManagementResponse>> getAllActiveMembers() {
        GetMemberManagementResponse response = memberQueryService.getAllActiveMembers();
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PatchMapping("/management/{memberId}/coupon")
    public ResponseEntity<ApiResult<String>> updateCoupon(
            @PathVariable Long memberId,
            @RequestBody @Valid UpdateCouponRequest request
    ) {
        commandService.updateCoupon(memberId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PatchMapping("/management/{memberId}/membership")
    public ResponseEntity<ApiResult<String>> updateMemberType(
            @PathVariable Long memberId,
            @RequestBody @Valid UpdateMemberTypeRequest request
    ) {
        commandService.updateMemberType(memberId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
