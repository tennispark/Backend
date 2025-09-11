package kr.tennispark.post.common.domain.entity;

import static kr.tennispark.common.domain.DomainValidator.requireNonBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "comment_report",
        indexes = {
                @Index(name = "idx_comment_report_comment", columnList = "comment_id, created_at"),
                @Index(name = "idx_comment_report_post", columnList = "post_id, created_at"),
                @Index(name = "idx_comment_report_reporter", columnList = "reporter_id, created_at")
        }
)
@SQLDelete(sql = "UPDATE comment_report SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class CommentReport extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    @Column(nullable = false)
    private LocalDateTime commentCreatedAt;

    @Column(nullable = false, length = 100)
    private String postTitle;

    @Lob
    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporter_id", nullable = false)
    private Member reporter;

    @Column(nullable = false, length = 1000)
    private String reason;

    private CommentReport(Comment comment, Member reporter, String reason) {
        this.comment = comment;
        this.post = comment.getPost();
        this.author = comment.getMember();
        this.commentCreatedAt = comment.getCreatedAt();
        this.postTitle = comment.getPost().getTitle();
        this.commentContent = comment.getContent();
        this.reporter = reporter;
        this.reason = requireNonBlank(reason);
    }

    public static CommentReport create(Comment comment, Member reporter, String reason) {
        if (comment == null) {
            throw new IllegalArgumentException("신고 대상 댓글이 없습니다.");
        }
        if (reporter == null) {
            throw new IllegalArgumentException("신고자가 없습니다.");
        }
        return new CommentReport(comment, reporter, reason);
    }
}