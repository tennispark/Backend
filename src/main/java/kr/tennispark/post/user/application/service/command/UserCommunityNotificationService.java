package kr.tennispark.post.user.application.service.command;

import static kr.tennispark.notification.admin.application.NotificationMessageFactory.communityCommentCreatedMessage;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.notification.admin.application.NotificationPublisher;
import kr.tennispark.post.common.domain.entity.Comment;
import kr.tennispark.post.common.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommunityNotificationService {

    private final NotificationPublisher publisher;

    @Transactional
    public void notifyCommentCreated(Comment comment) {
        if (comment == null) {
            return;
        }

        Post post = comment.getPost();
        if (!post.isNotificationEnabled()) {
            return;
        }

        Member author = post.getMember();
        if (author == null) {
            return;
        }

        Member commenter = comment.getMember();
        if (author.getId().equals(commenter.getId())) {
            return;
        }

        String content = communityCommentCreatedMessage(
                commenter != null ? commenter.getName() : "알 수 없음",
                post.getTitle()
        );

        publisher.notifyMemberForCommunity(author, content, post);
    }
}
