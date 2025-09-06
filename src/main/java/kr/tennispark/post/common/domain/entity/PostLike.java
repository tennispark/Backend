package kr.tennispark.post.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "post_like",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_post_like_post_member", columnNames = {"post_id", "member_id"})
        },
        indexes = {
                @Index(name = "idx_post_like_post_member", columnList = "post_id, member_id")
        }
)
public class PostLike extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean liked = false;

    private PostLike(Post post, Member member) {
        this.post = post;
        this.member = member;
        this.liked = false;
    }

    public static PostLike create(Post post, Member member) {
        return new PostLike(post, member);
    }

    public boolean toggleLike() {
        if (liked) {
            liked = false;
            post.decreaseLike();
        } else {
            liked = true;
            post.increaseLike();
        }
        return liked;
    }
}
