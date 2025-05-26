package kr.tennispark.event.presentation;

import jakarta.validation.Valid;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.event.application.EventAdminUseCase;
import kr.tennispark.event.presentation.dto.request.ManageEventRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventAdminUseCase eventAdminUseCase;

    @PostMapping("/events")
    public ResponseEntity<ApiResult<?>> registerEvent(@Valid @RequestBody ManageEventRequestDTO request) {
        eventAdminUseCase.registerEvent(request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<ApiResult<?>> modifyEventDetails(
            @PathVariable Long eventId,
            @Valid @RequestBody ManageEventRequestDTO request) {
        eventAdminUseCase.modifyEventDetails(eventId, request);
        return ResponseEntity.ok(ApiUtils.success());
    }


}
