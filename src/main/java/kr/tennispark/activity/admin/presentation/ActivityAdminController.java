package kr.tennispark.activity.admin.presentation;

import jakarta.validation.Valid;
import kr.tennispark.activity.admin.application.ActivityAdminUseCase;
import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseDTO;
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
    public ResponseEntity<ApiResult<GetActivityResponseDTO>> getActivityDetails(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        GetActivityResponseDTO response = actUseCase.getActivityList(page, size);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/activities")
    public ResponseEntity<ApiResult<?>> registerActivity(@RequestBody @Valid ManageActivityRequestDTO request) {
        actUseCase.registerActivity(request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PutMapping("/activities/{activityId}")
    public ResponseEntity<ApiResult<?>> registerActivity(@PathVariable Long activityId,
                                                         @RequestBody @Valid ManageActivityRequestDTO request) {
        actUseCase.modifyActivityDetails(activityId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
