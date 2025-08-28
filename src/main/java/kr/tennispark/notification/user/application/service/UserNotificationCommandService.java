package kr.tennispark.notification.user.application.service;

import java.time.LocalDateTime;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.notification.user.infrastructure.repository.UserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserNotificationCommandService {

    private final UserNotificationRepository userNotificationRepository;

    public void readAllNotifications(Member member) {
        LocalDateTime now = LocalDateTime.now();
        userNotificationRepository.markAllUnreadAsRead(member.getId(), now);
    }
}
