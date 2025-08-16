package kr.tennispark.notification.user.application.service;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.notification.common.domain.entity.Notification;
import kr.tennispark.notification.user.infrastructure.repository.UserNotificationRepository;
import kr.tennispark.notification.user.presentation.dto.GetMyNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserNotificationQueryService {

    private final UserNotificationRepository userNotificationRepository;

    public GetMyNotificationResponse getMyNotifications(Member member) {
        List<Notification> notifications = userNotificationRepository.findAllByMemberIdOrderByLatest(member.getId());
        return GetMyNotificationResponse.of(notifications);
    }
}
