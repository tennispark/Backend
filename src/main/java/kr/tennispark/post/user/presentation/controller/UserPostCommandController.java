package kr.tennispark.post.user.presentation.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.user.application.service.UserPostCommandService;
import kr.tennispark.post.user.presentation.dto.request.RegisterPostMultiPart;
import kr.tennispark.post.user.presentation.dto.request.SavePostDTO;
import kr.tennispark.post.user.presentation.dto.request.UpdatePostMultiPart;
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
@RequestMapping("/api/members/community/posts")
@RequiredArgsConstructor
public class UserPostCommandController {

    private final UserPostCommandService postCommandService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<String>> createPost(
            @LoginMember Member member,

            @Valid @RequestPart("data")
            SavePostDTO data,

            @Size(max = 3)
            @RequestPart(value = "photos", required = false)
            List<MultipartFile> photos) {
        RegisterPostMultiPart request = new RegisterPostMultiPart(data, photos);
        postCommandService.createPost(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @PutMapping(value = "/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<String>> updatePost(
            @PathVariable Long id,
            @LoginMember Member member,

            @Valid @RequestPart("data")
            SavePostDTO data,

            @Size(max = 3)
            @RequestPart(value = "photos", required = false)
            List<MultipartFile> photos) {
        UpdatePostMultiPart request = new UpdatePostMultiPart(data, photos);
        postCommandService.updatePost(member, id, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<String>> deletePost(
            @PathVariable Long id,
            @LoginMember Member member) {
        postCommandService.deletePost(member, id);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
