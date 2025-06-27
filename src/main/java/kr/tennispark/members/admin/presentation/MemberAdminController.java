package kr.tennispark.members.admin.presentation;

import java.time.LocalDate;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.admin.application.MemberAdminUseCase;
import kr.tennispark.members.admin.presentation.dto.response.GetMemberListResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetMonthlyMemberActivityStatsResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetOverallMemberStatsResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetTopMembersResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class MemberAdminController {

    private final MemberAdminUseCase memberAdminUseCase;

    @GetMapping()
    public ResponseEntity<ApiResult<GetMemberListResponseDTO>> getMemberList(
            @RequestParam String name) {
        return ResponseEntity.ok(ApiUtils.success(memberAdminUseCase.getMemberList(name)));
    }

    @GetMapping("member-activities/total")
    public ResponseEntity<ApiResult<GetOverallMemberStatsResponseDTO>> getMemberActivitiesTotal() {
        return ResponseEntity.ok(ApiUtils.success(memberAdminUseCase.getOverallMemberStats()));
    }

    @GetMapping("member-activities/monthly")
    public ResponseEntity<ApiResult<GetMonthlyMemberActivityStatsResponseDTO>> getMonthlyMemberActivityStats(
            @RequestParam LocalDate from, @RequestParam LocalDate to) {
        return ResponseEntity.ok(ApiUtils.success(memberAdminUseCase.getMonthlyActivityStats(from, to)));
    }

    @GetMapping("member-activities/top")
    public ResponseEntity<ApiResult<GetTopMembersResponseDTO>> getTopMembers() {
        return ResponseEntity.ok(ApiUtils.success(memberAdminUseCase.getTopMembers()));
    }
}
