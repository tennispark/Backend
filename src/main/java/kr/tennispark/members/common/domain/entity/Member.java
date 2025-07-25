package kr.tennispark.members.common.domain.entity;

import static io.micrometer.common.util.StringUtils.isBlank;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.enums.Gender;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;
import kr.tennispark.members.common.domain.entity.enums.RegistrationSource;
import kr.tennispark.members.common.domain.entity.vo.Phone;
import kr.tennispark.members.common.domain.exception.InvalidMemberException;
import kr.tennispark.point.common.domain.entity.Point;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "member",
        indexes = {
                @Index(name = "idx_member_phone_number", columnList = "phone_number")
        }
)
@SQLDelete(sql = "UPDATE member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Member extends BaseEntity {

    @Embedded
    private Phone phone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private Integer tennisCareer;

    private String recommender;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "point_id", nullable = false)
    private Point point;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer matchPoint = 0;

    @Column(nullable = false)
    private String instagramId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'GUEST'")
    private MemberShipType memberShipType = MemberShipType.GUEST;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistrationSource registrationSource;

    @Column(name = "fcm_token")
    private String fcmToken;

    private Member(Phone phone, String name, int year, Integer tennisCareer, String recommender,
                   String instagramId, Gender gender, RegistrationSource registrationSource) {
        validateRecommender(registrationSource, recommender);
        this.phone = phone;
        this.name = name;
        this.year = year;
        this.tennisCareer = tennisCareer;
        this.recommender = recommender;
        this.point = Point.of(this);
        this.matchPoint = 0;
        this.instagramId = instagramId;
        this.gender = gender;
        this.registrationSource = registrationSource;
    }

    public static Member of(Phone phone, String name, int year, Integer tennisCareer, String recommender,
                            String instagramId, Gender gender, RegistrationSource registrationSource) {
        return new Member(
                phone,
                name,
                year,
                tennisCareer,
                recommender,
                instagramId,
                gender,
                registrationSource
        );
    }

    private void validateRecommender(RegistrationSource registrationSource, String recommender) {
        if (registrationSource == RegistrationSource.FRIEND_RECOMMENDATION
                && (isBlank(recommender))) {
            throw new InvalidMemberException("가입 경로가 친구 추천인 경우, 추천인 아이디는 필수입니다.");
        }
    }

    public void increaseMatchPoint(Integer point) {
        this.matchPoint += point;
    }

    public void updateMemberShipType(MemberShipType memberShipType) {
        this.memberShipType = memberShipType;
    }

    public void updateFcmToken(String fcmToken) {
        if (!StringUtils.isBlank(fcmToken)) {
            this.fcmToken = fcmToken;
        } else {
            this.fcmToken = null;
        }
    }

    public void withdraw() {
        if (!isStatus()) {
            return;
        }
        this.phone = this.phone.withdrawMasked(this.getId());
        delete();
        point.delete();
    }
}
