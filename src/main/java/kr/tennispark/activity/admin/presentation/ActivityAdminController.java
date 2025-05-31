package kr.tennispark.activity.admin.presentation;

import jakarta.validation.Valid;
import kr.tennispark.activity.admin.application.ActivityAdminUseCase;
import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityInfoRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityApplicationResponseDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseInfoDTO;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ActivityAdminController {

    private final ActivityAdminUseCase actUseCase;

    @GetMapping("/activities")
    public ResponseEntity<ApiResult<GetActivityResponseInfoDTO>> getActivityInfoDetails(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        GetActivityResponseInfoDTO response = actUseCase.getActivityInfoList(page, size);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/activities/applications")
    public ResponseEntity<ApiResult<GetActivityApplicationResponseDTO>> getActivityApplicationDetails(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        GetActivityApplicationResponseDTO response = actUseCase.getActivityApplicationList(page, size);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/activities")
    public ResponseEntity<ApiResult<?>> registerActivityInfo(@RequestBody @Valid ManageActivityInfoRequestDTO request) {
        actUseCase.registerActivityInfo(request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PutMapping("/activities/{activityId}")
    public ResponseEntity<ApiResult<?>> modifyActivityInfo(@PathVariable Long activityId,
                                                           @RequestBody @Valid ManageActivityInfoRequestDTO request) {
        actUseCase.modifyActivityInfoDetails(activityId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
