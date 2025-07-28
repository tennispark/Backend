package kr.tennispark.activity.user.presentation.controller;

import kr.tennispark.activity.user.application.service.ActivityImageUserService;
import kr.tennispark.activity.user.presentation.dto.response.GetActivityImageResponse;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/activities/images")
public class ActivityImageUserController {

    private final ActivityImageUserService activityImageUserService;

    @GetMapping
    public ResponseEntity<ApiResult<GetActivityImageResponse>> getActivityImage() {
        return ResponseEntity.ok(ApiUtils.success(activityImageUserService.getActivityImage()));
    }
}
