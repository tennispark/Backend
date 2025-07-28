package kr.tennispark.activity.admin.presentation;

import kr.tennispark.activity.admin.application.impl.ActivityImageAdminService;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/activities/images")
public class ActivityImageAdminController {

    private final ActivityImageAdminService activityImageAdminService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<?>> saveActivityImage(
            @RequestPart MultipartFile image) {
        activityImageAdminService.saveActivityImage(image);
        return ResponseEntity.ok(ApiUtils.success());
    }


}
