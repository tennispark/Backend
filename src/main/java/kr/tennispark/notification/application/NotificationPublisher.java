package kr.tennispark.notification.application;

import java.util.Collection;
import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.notification.domain.entity.Notification;
import kr.tennispark.notification.domain.entity.enums.NotificationCategory;
import kr.tennispark.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotificationPublisher {

    private static final int BATCH_SIZE = 500;

    private final FcmMessageService fcm;
    private final NotificationRepository notificationRepo;
    private final MemberRepository memberRepo;

    @Transactional
    public void notifyMembers(
            Collection<Member> members,
            NotificationCategory category,
            String content
    ) {
        List<Notification> rows = members.stream()
                .filter(m -> m != null && !m.isDeleted())
                .map(m -> Notification.of(m, category, content))
                .toList();
        batchSave(rows);

        List<String> tokens = members.stream()
                .map(Member::getFcmToken)
                .filter(t -> t != null && !t.isBlank())
                .distinct()
                .toList();
        fcm.sendMessage(tokens, content);
    }

    @Transactional
    public void broadcast(String content) {
        List<Member> members = memberRepo.findAllWithValidFcmToken();
        notifyMembers(members, NotificationCategory.ANNOUNCEMENT, content);
    }

    private void batchSave(List<Notification> rows) {
        for (int i = 0; i < rows.size(); i += BATCH_SIZE) {
            int to = Math.min(i + BATCH_SIZE, rows.size());
            notificationRepo.saveAll(rows.subList(i, to));
        }
    }
}
