package kr.tennispark.event.user.presentation;

import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.event.user.application.EventMemberUseCase;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class EventMemberController {

    private final EventMemberUseCase eventMemberUseCase;

    @PostMapping("/events/attend")
    public ResponseEntity<ApiResult<?>> attendEvent(
            @RequestParam Long eventId,
            @LoginMember Member member) {
        eventMemberUseCase.attendEvent(eventId, member);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
