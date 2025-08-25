package kr.tennispark.activity.common.domain.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    PENDING(false),
    WAITING(false),
    APPROVED(true),
    CANCELED(false);

    private final boolean counted;

    ApplicationStatus(boolean counted) {
        this.counted = counted;
    }

    public boolean isAccepted() {
        return this == APPROVED;
    }

    public boolean isPending() {
        return this == PENDING;
    }
}
