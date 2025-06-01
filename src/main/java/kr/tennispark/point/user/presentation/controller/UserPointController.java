package kr.tennispark.point.user.presentation.controller;

import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.user.application.service.UserPointService;
import kr.tennispark.point.user.presentation.dto.response.GetMemberPointHistoryResponse;
import kr.tennispark.point.user.presentation.dto.response.GetMemberPointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/points")
@RequiredArgsConstructor
public class UserPointController {

    private final UserPointService pointService;

    @GetMapping("/me")
    public ResponseEntity<ApiResult<GetMemberPointResponse>> getMyPoint(@LoginMember Member member){
        GetMemberPointResponse response = pointService.getMemberPoint(member);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/me/history")
    public ResponseEntity<ApiResult<GetMemberPointHistoryResponse>> getMyPointHistory(@LoginMember Member member){
        GetMemberPointHistoryResponse response = pointService.getMemberPointHistory(member);
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
