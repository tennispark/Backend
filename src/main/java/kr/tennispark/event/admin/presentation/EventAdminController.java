package kr.tennispark.event.admin.presentation;

import jakarta.validation.Valid;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.event.admin.application.EventAdminUseCase;
import kr.tennispark.event.admin.presentation.dto.request.ManageEventRequestDTO;
import kr.tennispark.event.admin.presentation.dto.response.GetEventResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventAdminUseCase eventAdminUseCase;

    @GetMapping("/events")
    public ResponseEntity<ApiResult<GetEventResponseDTO>> getAllEvents(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiUtils.success(eventAdminUseCase.getAllEvents(page, size)));
    }

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

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<ApiResult<?>> removeEvent(
            @PathVariable Long eventId
    ) {
        eventAdminUseCase.removeEvent(eventId);
        return ResponseEntity.ok(ApiUtils.success());
    }


}
