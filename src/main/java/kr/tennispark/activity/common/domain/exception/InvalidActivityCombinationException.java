package kr.tennispark.activity.common.domain.exception;

import kr.tennispark.activity.common.domain.enums.ActivityName;
import kr.tennispark.activity.common.domain.enums.ActivityType;
import kr.tennispark.common.exception.base.InvalidException;

public class InvalidActivityCombinationException extends InvalidException {

    public InvalidActivityCombinationException(String message) {
        super(message);
    }

    public InvalidActivityCombinationException(ActivityType type, ActivityName name) {
        super("잘못된 조합: " + type + " 활동에는 " + name + " 을(를) 사용할 수 없습니다.");
    }
}
