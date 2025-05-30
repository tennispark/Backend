package kr.tennispark.advertisement.admin.presentation;

import kr.tennispark.advertisement.admin.application.AdvertisementAdminUseCase;
import kr.tennispark.advertisement.admin.presentation.dto.response.GetAdvertisementResponseDTO;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementAdminUseCase advertisementAdminUseCase;

    @PostMapping
    public ResponseEntity<ApiResult<?>> saveAdvertisement(
            @RequestParam MultipartFile imageFile,
            @RequestParam Position position
    ) {
        advertisementAdminUseCase.saveAdvertisement(imageFile, position);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @DeleteMapping("/{advertisementId}")
    public ResponseEntity<ApiResult<?>> deleteAdvertisement(
            @PathVariable Long advertisementId
    ) {
        advertisementAdminUseCase.deleteAdvertisement(advertisementId);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @GetMapping
    public ResponseEntity<ApiResult<GetAdvertisementResponseDTO>> getAdvertisementsByPosition(
            @RequestParam Position position
    ) {
        GetAdvertisementResponseDTO response = advertisementAdminUseCase.getAdvertisementsByPosition(position);
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
