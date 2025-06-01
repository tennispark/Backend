package kr.tennispark.membership.common.domain.entity.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import kr.tennispark.membership.common.domain.exception.InvalidActivityDurationException;
import lombok.Getter;

@Getter
public enum ActivityDuration {
    WEEKS_7("7WEEKS"),
    WEEKS_9("9WEEKS"),
    WEEKS_13("13WEEKS");

    private final String value;

    ActivityDuration(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ActivityDuration from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new InvalidActivityDurationException(value));
    }
}