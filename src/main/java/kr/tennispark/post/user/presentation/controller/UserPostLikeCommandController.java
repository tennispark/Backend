package kr.tennispark.post.user.presentation.controller;

import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.user.application.service.command.UserPostLikeCommandService;
import kr.tennispark.post.user.presentation.dto.response.CreatePostLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/community/posts")
@RequiredArgsConstructor
public class UserPostLikeCommandController {

    private final UserPostLikeCommandService likeService;

    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResult<CreatePostLikeResponse>> createPostLike(
            @PathVariable Long postId,
            @LoginMember Member member
    ) {
        CreatePostLikeResponse response = likeService.createPostLike(member, postId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

}
