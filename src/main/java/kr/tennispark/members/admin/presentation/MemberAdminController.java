package kr.tennispark.members.admin.presentation;

import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.admin.application.MemberAdminUseCase;
import kr.tennispark.members.admin.presentation.dto.response.GetMemberListResponseDTO;
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


}
