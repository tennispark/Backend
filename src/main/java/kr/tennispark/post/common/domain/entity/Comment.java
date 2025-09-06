package kr.tennispark.post.common.domain.entity;

import static kr.tennispark.common.domain.DomainValidator.requireNonBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_comment")
@SQLRestriction("status = true")
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(name = "photo_url", length = 1000)
    private String photoUrl;

    private Comment(Post post, Member member, String content, String photoUrl) {
        this.post = post;
        this.member = member;
        this.content = requireNonBlank(content);
        this.photoUrl = photoUrl;
        this.post.increaseComment();
    }

    public static Comment create(Post post, Member member, String content, String photoUrl) {
        return new Comment(post, member, content, photoUrl);
    }

    public void update(String newContent, String photoUrl) {
        this.content = requireNonBlank(newContent);
        this.photoUrl = photoUrl;
    }

    public void remove() {
        if (!isDeleted()) {
            this.post.decreaseComment();
            delete();
        }
    }
}