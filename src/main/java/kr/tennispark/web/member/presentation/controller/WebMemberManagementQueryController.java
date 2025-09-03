package kr.tennispark.web.member.presentation.controller;

import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.web.member.application.service.WebMemberManagementQueryService;
import kr.tennispark.web.member.presentation.dto.GetMemberManagementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class WebMemberManagementQueryController {

    private final WebMemberManagementQueryService memberQueryService;

    @GetMapping("/management")
    public ResponseEntity<ApiResult<GetMemberManagementResponse>> getAllActiveMembers() {
        GetMemberManagementResponse response = memberQueryService.getAllActiveMembers();
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
