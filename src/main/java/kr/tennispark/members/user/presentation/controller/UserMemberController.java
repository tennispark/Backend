package kr.tennispark.members.user.presentation.controller;

import jakarta.validation.Valid;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.application.service.MemberService;
import kr.tennispark.members.user.presentation.dto.request.UpdateFcmTokenRequestDTO;
import kr.tennispark.members.user.presentation.dto.response.GetMemberMatchRecordResponse;
import kr.tennispark.members.user.presentation.dto.response.GetMyNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class UserMemberController {

    private final MemberService memberService;

    @GetMapping("/name/me")
    public ResponseEntity<ApiResult<GetMyNameResponse>> getMyName(@LoginMember Member member) {
        GetMyNameResponse response = new GetMyNameResponse(member.getName());
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/matches/me")
    public ResponseEntity<ApiResult<GetMemberMatchRecordResponse>> getMatches(@LoginMember Member member) {
        GetMemberMatchRecordResponse response = memberService.getMemberMatchRecord(member);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/fcm-token")
    public ResponseEntity<ApiResult<?>> updateFcmToken(
            @LoginMember Member member,
            @RequestBody @Valid UpdateFcmTokenRequestDTO request
    ) {
        memberService.updateFcmToken(member, request.fcmToken());
        return ResponseEntity.ok(ApiUtils.success());
    }
}
