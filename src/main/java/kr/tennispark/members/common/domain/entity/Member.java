package kr.tennispark.members.common.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.tennispark.common.domain.BaseEntity;
import kr.tennispark.members.common.domain.entity.enums.Gender;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;
import kr.tennispark.members.common.domain.entity.enums.RegistrationSource;
import kr.tennispark.members.common.domain.entity.vo.Email;
import kr.tennispark.members.common.domain.entity.vo.Phone;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Member extends BaseEntity {

    @Embedded
    private Email email;

    @Embedded
    private Phone phone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String tennisCareer;

    private String recommender;

    @Column(nullable = false)
    private String instagramId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("GUEST")
    private MemberShipType memberShipType = MemberShipType.GUEST;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistrationSource registrationSource;
}
