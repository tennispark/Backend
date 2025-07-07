package kr.tennispark.activity.common.domain.enums;

import kr.tennispark.activity.common.domain.exception.InvalidActivityCombinationException;
import lombok.Getter;

@Getter
public enum ActivityName {

    // 일반 활동
    GAME(ActivityType.GENERAL),
    CHALLENGE(ActivityType.GENERAL),
    RALLY(ActivityType.GENERAL),
    STUDY(ActivityType.GENERAL),
    BEGINNER(ActivityType.GENERAL),

    // 아카데미
    FOREHAND_BACKHAND(ActivityType.ACADEMY),
    VOLLEY_SERVE(ActivityType.ACADEMY);

    private final ActivityType validType;

    ActivityName(ActivityType validType) {
        this.validType = validType;
    }

    public void validateWith(ActivityType type) {
        if (this.validType != type) {
            throw new InvalidActivityCombinationException(type, this);
        }
    }
}
