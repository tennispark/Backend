package kr.tennispark.act.admin.presentation;

import jakarta.validation.Valid;
import kr.tennispark.act.admin.application.ActAdminUseCase;
import kr.tennispark.act.admin.presentation.dto.request.ManageActRequestDTO;
import kr.tennispark.act.admin.presentation.dto.response.GetActResponseDTO;
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
public class ActAdminController {

    private final ActAdminUseCase actUseCase;

    @GetMapping("/acts")
    public ResponseEntity<ApiResult<GetActResponseDTO>> getActDetails(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        GetActResponseDTO response = actUseCase.getActList(page, size);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/acts")
    public ResponseEntity<ApiResult<?>> registerAct(@RequestBody @Valid ManageActRequestDTO request) {
        actUseCase.registerAct(request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PutMapping("/acts/{actId}")
    public ResponseEntity<ApiResult<?>> registerAct(@PathVariable Long actId,
                                                    @RequestBody @Valid ManageActRequestDTO request) {
        actUseCase.modifyActDetails(actId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
