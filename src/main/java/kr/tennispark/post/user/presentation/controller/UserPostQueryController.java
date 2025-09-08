package kr.tennispark.post.user.presentation.controller;

import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.user.application.service.query.UserPostQueryService;
import kr.tennispark.post.user.presentation.dto.response.GetPostDetailResponse;
import kr.tennispark.post.user.presentation.dto.response.PostHomeItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/community/posts")
@RequiredArgsConstructor
public class UserPostQueryController {

    private final UserPostQueryService postQueryService;

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResult<GetPostDetailResponse>> getPostDetail(@PathVariable Long postId,
                                                                          @LoginMember Member member) {
        GetPostDetailResponse response = postQueryService.getPostDetail(member, postId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/home")
    public ResponseEntity<ApiResult<Slice<PostHomeItemResponse>>> getHome(
            @LoginMember Member member,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(ApiUtils.success(postQueryService.getHome(member, pageable)));
    }
}
