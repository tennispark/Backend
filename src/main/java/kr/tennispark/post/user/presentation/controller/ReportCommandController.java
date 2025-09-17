package kr.tennispark.post.user.presentation.controller;

import jakarta.validation.Valid;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.user.application.service.command.UserReportCommandService;
import kr.tennispark.post.user.presentation.dto.request.ReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/community")
public class ReportCommandController {

    private final UserReportCommandService reportService;

    @PostMapping("/posts/{postId}/reports")
    public ResponseEntity<ApiResult<String>> reportPost(@PathVariable Long postId,
                                                        @LoginMember Member member,
                                                        @RequestBody @Valid ReportRequest request) {
        reportService.reportPost(member, postId, request.reason());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/reports")
    public ResponseEntity<ApiResult<String>> reportComment(@PathVariable Long postId,
                                                           @PathVariable Long commentId,
                                                           @LoginMember Member member,
                                                           @RequestBody @Valid ReportRequest request) {
        reportService.reportComment(member, postId, commentId, request.reason());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
    }
}
