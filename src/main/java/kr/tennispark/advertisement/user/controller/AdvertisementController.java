package kr.tennispark.advertisement.user.controller;


import kr.tennispark.advertisement.admin.application.AdvertisementAdminUseCase;
import kr.tennispark.advertisement.admin.presentation.dto.response.GetAdvertisementResponseDTO;
import kr.tennispark.advertisement.common.domain.entity.enums.Position;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementAdminUseCase advertisementAdminUseCase;

    @GetMapping
    public ResponseEntity<ApiResult<GetAdvertisementResponseDTO>> getAdvertisementsByPosition(
            @RequestParam Position position
    ) {
        GetAdvertisementResponseDTO response = advertisementAdminUseCase.getAdvertisementsByPosition(position);
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
