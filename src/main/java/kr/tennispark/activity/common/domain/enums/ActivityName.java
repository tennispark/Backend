package kr.tennispark.activity.common.domain.enums;

import kr.tennispark.activity.common.domain.exception.InvalidActivityCombinationException;
import lombok.Getter;

@Getter
public enum ActivityName {

    // 일반 활동
    GAME(ActivityType.GENERAL),
    GAME_CHALLENGE(ActivityType.GENERAL),
    RALLY(ActivityType.GENERAL),
    GAME_STUDY(ActivityType.GENERAL),
    BEGINNER(ActivityType.GENERAL),

    // 아카데미
    LEVEL1(ActivityType.ACADEMY),
    LEVEL2(ActivityType.ACADEMY),
    LEVEL3(ActivityType.ACADEMY);

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
