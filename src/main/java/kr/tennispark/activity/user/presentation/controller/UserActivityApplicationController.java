package kr.tennispark.activity.user.presentation.controller;

import kr.tennispark.activity.user.application.service.ActivityApplicationQueryService;
import kr.tennispark.activity.user.presentation.dto.response.GetMyApplicationsResponse;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/activities/applications")
public class UserActivityApplicationController {

    private final ActivityApplicationQueryService activityApplicationQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiResult<GetMyApplicationsResponse>> getMyApplications(
            @LoginMember Member member,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        GetMyApplicationsResponse response = activityApplicationQueryService.getMyApplications(member, page, size);

        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
