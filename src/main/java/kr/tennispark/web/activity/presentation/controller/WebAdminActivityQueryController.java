package kr.tennispark.web.activity.presentation.controller;

import kr.tennispark.activity.common.domain.enums.ActivityType;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.web.activity.application.service.WebActivityApplicationQueryService;
import kr.tennispark.web.activity.presentation.dto.GetWeeklyActivityApplicationsResponse;
import kr.tennispark.web.activity.presentation.dto.GetActivityApplicationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/activities")
public class WebAdminActivityQueryController {

    private final WebActivityApplicationQueryService activityApplicationQueryService;

    /**
     * 기존 API — 이번 주 활동 조회
     */
    @GetMapping("/applications/week")
    public ResponseEntity<ApiResult<GetWeeklyActivityApplicationsResponse>> getWeeklyApplications(
            @RequestParam ActivityType type
    ) {
        GetWeeklyActivityApplicationsResponse response =
                activityApplicationQueryService.getThisWeeksApplication(type);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    /**
     * 신규 API — 기간 조회 (/applications/search)
     * LocalDate 파싱 명확화(@DateTimeFormat)
     */
    @GetMapping("/applications/search")
    public ResponseEntity<ApiResult<GetActivityApplicationsResponse>> searchApplications(
            @RequestParam ActivityType type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        GetActivityApplicationsResponse response =
                activityApplicationQueryService.getApplications(type, fromDate, toDate);
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
