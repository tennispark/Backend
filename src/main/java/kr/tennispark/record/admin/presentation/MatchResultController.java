package kr.tennispark.record.admin.presentation;

import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.record.admin.application.MatchResultService;
import kr.tennispark.record.admin.presentation.dto.request.SaveMatchResultRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class MatchResultController {

    private final MatchResultService matchResultService;

    @PostMapping("/match-results")
    public ResponseEntity<ApiResult<?>> saveMatchResult(
            @RequestBody SaveMatchResultRequestDTO request
    ) {
        matchResultService.saveMatchResult(request);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
