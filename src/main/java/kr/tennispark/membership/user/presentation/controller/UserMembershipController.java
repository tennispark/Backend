package kr.tennispark.membership.user.presentation.controller;

import jakarta.validation.Valid;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.membership.application.service.MembershipService;
import kr.tennispark.membership.user.presentation.dto.request.RegisterMembershipRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/memberships")
@RequiredArgsConstructor
public class UserMembershipController {

    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerMembership(
            @LoginMember Member member,
            @Valid @RequestBody RegisterMembershipRequest request
    ) {
        membershipService.registerMembership(member, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
    }
}
