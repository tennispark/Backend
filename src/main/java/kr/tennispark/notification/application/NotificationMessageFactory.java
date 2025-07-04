package kr.tennispark.notification.application;

import java.time.LocalTime;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.notification.domain.entity.enums.NotificationType;
import org.springframework.stereotype.Service;

@Service
public class NotificationMessageFactory {

    private static final String COMMON_SUFFIX = "님들과 테니스 활동 예정입니다. 다른분들을 위해 꼭 늦지 않고 도착해주세요.";
    private static final String ONE_DAY_FORMAT = "내일 %s %s %s에서 %s " + COMMON_SUFFIX;
    private static final String ONE_HOUR_FORMAT = "잠시후 %s %s %s에서 %s " + COMMON_SUFFIX;
    private static final String RECRUIT_PREFIX = "이번주 테니스파크 활동 추가모집합니다. ";
    private static final String RECRUIT_FORMAT = "%s %s %d석";
    private static final String TIME_FORMAT = "%02d시";
    private static final String DELIMITER = ", ";

    public static String createMessage(NotificationType type, Activity activity, List<String> participantNames) {
        String time = formatTime(activity.getScheduledTime().getBeginAt());
        String place = activity.getPlace().getName();
        String courtName = activity.getCourtName();
        int remainingSeats = activity.getCapacity() - activity.getParticipantCount();

        String names = formatNames(participantNames);

        return switch (type) {
            case ONE_DAY_BEFORE -> String.format(
                    ONE_DAY_FORMAT, time, place, courtName, names
            );
            case ONE_HOUR_BEFORE -> String.format(
                    ONE_HOUR_FORMAT, time, place, courtName, names
            );
            case RECRUIT_REMINDER -> String.format(
                    RECRUIT_PREFIX, place, remainingSeats
            );
        };
    }

    public static String createRecruitReminderMessage(List<Activity> activities) {
        StringBuilder message = new StringBuilder(RECRUIT_PREFIX);

        for (Activity activity : activities) {
            String place = activity.getPlace().getName();
            String courtType = activity.getCourtName();
            int remainingSeats = activity.getCapacity() - activity.getParticipantCount();

            message.append(String.format(RECRUIT_FORMAT, place, courtType, remainingSeats)).append(DELIMITER);
        }

        removeDelimiter(message);
        return message.toString();
    }

    private static void removeDelimiter(StringBuilder message) {
        if (message.toString().endsWith(DELIMITER)) {
            message.setLength(message.length() - DELIMITER.length());
        }
    }

    private static String formatTime(LocalTime time) {
        return String.format(TIME_FORMAT, time.getHour());
    }

    private static String formatNames(List<String> names) {
        return String.join(DELIMITER, names);
    }
}
