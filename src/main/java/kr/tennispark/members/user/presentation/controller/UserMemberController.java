package kr.tennispark.members.user.presentation.controller;

import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.presentation.dto.response.GetMyNameResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class UserMemberController {

    @GetMapping("/name/me")
    public ResponseEntity<ApiResult<GetMyNameResponse>> getMyName(@LoginMember Member member) {
        GetMyNameResponse response = new GetMyNameResponse(member.getName());
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
