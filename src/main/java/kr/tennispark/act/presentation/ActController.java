package kr.tennispark.act.presentation;

import jakarta.validation.Valid;
import kr.tennispark.act.application.ManageActUseCase;
import kr.tennispark.act.presentation.dto.request.ManageActRequestDTO;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ActController {

    private final ManageActUseCase manageActUseCase;

    @PostMapping("/admin/acts")
    public ResponseEntity<ApiResult<?>> registerAct(@RequestBody @Valid ManageActRequestDTO request) {
        manageActUseCase.registerAct(request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PutMapping("/admin/acts/{actId}")
    public ResponseEntity<ApiResult<?>> registerAct(@PathVariable Long actId,
                                                    @RequestBody @Valid ManageActRequestDTO request) {
        manageActUseCase.modifyActDetails(actId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
