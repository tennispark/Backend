package kr.tennispark.record.admin.presentation;

import jakarta.validation.Valid;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.record.admin.application.MatchResultService;
import kr.tennispark.record.admin.presentation.dto.request.SaveMatchResultRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class MatchResultController {

    private final MatchResultService matchResultService;

    @PostMapping("/match-results")
    public ResponseEntity<ApiResult<?>> saveMatchResult(
            @RequestBody @Valid SaveMatchResultRequestDTO request
    ) {
        matchResultService.saveMatchResult(request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @GetMapping("/match-results/members")
    public ResponseEntity<ApiResult<?>> getMatchResultMembers(@RequestParam String name) {

        return ResponseEntity.ok(ApiUtils.success(matchResultService.searchMemberNameForMatchResult(name)));
    }
}
