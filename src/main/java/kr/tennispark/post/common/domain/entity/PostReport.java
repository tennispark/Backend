package kr.tennispark.post.common.domain.entity;

import static kr.tennispark.common.domain.DomainValidator.requireNonBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "post_report")
@SQLDelete(sql = "UPDATE post_report SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class PostReport extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Member author;

    @Column(nullable = false)
    private LocalDateTime postCreatedAt;

    @Column(nullable = false, length = 100)
    private String postTitle;

    @Lob
    @Column(nullable = false)
    private String postContent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporter_id", nullable = false)
    private Member reporter;

    @Column(nullable = false, length = 1000)
    private String reason;

    private PostReport(Post post, Member reporter, String reason) {
        this.post = post;
        this.author = post.getMember();
        this.postCreatedAt = post.getCreatedAt();
        this.postTitle = post.getTitle();
        this.postContent = post.getContent();
        this.reporter = reporter;
        this.reason = requireNonBlank(reason);
    }

    public static PostReport create(Post post, Member reporter, String reason) {
        if (post == null) {
            throw new IllegalArgumentException("신고 대상 게시물이 없습니다.");
        }
        if (reporter == null) {
            throw new IllegalArgumentException("신고자가 없습니다.");
        }
        return new PostReport(post, reporter, reason);
    }
}
