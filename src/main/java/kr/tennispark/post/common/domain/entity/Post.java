package kr.tennispark.post.common.domain.entity;


import static kr.tennispark.common.domain.DomainValidator.requireNonBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.post.common.domain.entity.vo.Photos;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "community_post")
@SQLDelete(sql = "UPDATE community_post SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Post extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 3000)
    private String content;

    @Embedded
    private Photos photos;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean notificationEnabled = true;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer commentCount = 0;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer likeCount = 0;

    public static Post create(Member member, String title, String content,
                              Photos photos) {
        Post p = new Post();
        p.member = member;
        p.title = requireNonBlank(title);
        p.content = requireNonBlank(content);
        p.photos = photos != null ? photos : Photos.of(null);
        return p;
    }

    public void update(String title, String content,
                       Photos photos) {
        this.title = requireNonBlank(title);
        this.content = requireNonBlank(content);
        if (photos != null) {
            this.photos = photos;
        }
    }

    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void increaseComment() {
        this.commentCount++;
    }

    public void decreaseComment() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }
}

