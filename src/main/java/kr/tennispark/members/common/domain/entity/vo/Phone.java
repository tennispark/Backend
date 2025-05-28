package kr.tennispark.members.common.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.isBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
import kr.tennispark.members.common.domain.exception.InvalidMemberException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Phone {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^010\\d{8}$");

    @Column(name = "phone_number", nullable = false, unique = true)
    private String number;

    public static Phone of(String value) {
        if (isBlank(value) || !PHONE_PATTERN.matcher(value).matches()) {
            throw new InvalidMemberException("휴대폰 번호 형식이 올바르지 않습니다. 예: 01012345678");
        }
        return new Phone(value);
    }
}
