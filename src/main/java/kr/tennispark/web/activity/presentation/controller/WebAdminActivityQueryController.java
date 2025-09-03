package kr.tennispark.web.activity.presentation.controller;

import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.web.activity.application.service.WebActivityApplicationQueryService;
import kr.tennispark.web.activity.presentation.dto.GetWeeklyActivityApplicationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/activities")
public class WebAdminActivityQueryController {

    private final WebActivityApplicationQueryService activityApplicationQueryService;

    @GetMapping("/applications/week")
    public ResponseEntity<ApiResult<GetWeeklyActivityApplicationsResponse>> getWeeklyApplications() {
        GetWeeklyActivityApplicationsResponse response = activityApplicationQueryService.getThisWeeksApplication();
        return ResponseEntity.ok(ApiUtils.success(response));
    }

}
