package kr.tennispark.activity.user.presentation.controller;

import kr.tennispark.activity.user.application.service.ActivityCertificationService;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/members/activities/certifications")
@RequiredArgsConstructor
public class UserActivityCertificationController {

    private final ActivityCertificationService service;

    @PostMapping
    public ResponseEntity<ApiResult<String>> certify(
            @LoginMember Member member,
            @RequestPart MultipartFile image
    ) {
        service.certifyActivity(member, image);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
