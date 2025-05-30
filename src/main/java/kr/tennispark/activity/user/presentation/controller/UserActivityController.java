package kr.tennispark.activity.user.presentation.controller;


import kr.tennispark.activity.user.application.service.ActivityCommandService;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/activities")
public class UserActivityController {

    private final ActivityCommandService commandService;

    @PostMapping("/{activityId}/apply")
    public ResponseEntity<ApiResult<String>> applyActivity(@LoginMember Member member,
                                                           @PathVariable Long activityId) {
        commandService.applyActivity(member, activityId);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
