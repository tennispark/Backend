package kr.tennispark.post.user.presentation.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import jakarta.validation.Valid;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.user.application.service.UserPostCommentCommandService;
import kr.tennispark.post.user.presentation.dto.request.CreateCommentDTO;
import kr.tennispark.post.user.presentation.dto.request.RegisterCommentMultiPart;
import kr.tennispark.post.user.presentation.dto.request.UpdateCommentDTO;
import kr.tennispark.post.user.presentation.dto.request.UpdateCommentMultiPart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/members/community/posts/{postId}/comments")
@RequiredArgsConstructor
public class UserPostCommentCommandController {

    private final UserPostCommentCommandService commentService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<String>> createComment(
            @PathVariable Long postId,
            @LoginMember Member member,
            @Valid @RequestPart("data") CreateCommentDTO data,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        commentService.createComment(member, postId, new RegisterCommentMultiPart(data, photo));
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
    }

    @PutMapping(value = "/{commentId}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<String>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @LoginMember Member member,
            @Valid @RequestPart("data") UpdateCommentDTO data,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        commentService.updateComment(member, postId, commentId, new UpdateCommentMultiPart(data, photo));
        return ResponseEntity.ok(ApiUtils.success());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResult<String>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @LoginMember Member member
    ) {
        commentService.deleteComment(member, postId, commentId);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
