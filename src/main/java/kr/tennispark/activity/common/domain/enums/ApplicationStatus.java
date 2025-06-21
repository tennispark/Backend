package kr.tennispark.activity.common.domain.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    PENDING(true),
    APPROVED(true),
    CANCELED(false);

    private final boolean counted;

    ApplicationStatus(boolean counted) {
        this.counted = counted;
    }
}
