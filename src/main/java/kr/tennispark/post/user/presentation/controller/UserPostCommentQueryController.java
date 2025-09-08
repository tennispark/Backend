package kr.tennispark.post.user.presentation.controller;

import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.user.application.service.query.UserCommentQueryService;
import kr.tennispark.post.user.presentation.dto.response.GetCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/community/posts/{postId}/comments")
@RequiredArgsConstructor
public class UserPostCommentQueryController {

    private final UserCommentQueryService userCommentQueryService;

    @GetMapping
    public ResponseEntity<GetCommentResponse> getCommentsForPost(
            @PathVariable Long postId,
            @LoginMember Member member
    ) {
        return ResponseEntity.ok(userCommentQueryService.getCommentsForPost(postId, member));
    }
}
