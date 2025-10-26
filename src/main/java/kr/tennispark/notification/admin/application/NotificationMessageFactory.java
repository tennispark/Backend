package kr.tennispark.notification.admin.application;

import java.time.LocalTime;
import java.util.List;
import java.util.StringJoiner;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.common.utils.ActivityHeaderFormat;
import kr.tennispark.notification.common.domain.entity.enums.NotificationType;
import org.springframework.stereotype.Service;

@Service
public class NotificationMessageFactory {

    private static final String COMMON_SUFFIX = "님들과 테니스 활동 예정입니다. 다른분들을 위해 꼭 늦지 않고 도착해주세요.";
    // 시간 포맷 자리에 %s(문자열) 사용으로 교체
    private static final String ONE_DAY_FORMAT = "내일 %s %s %s에서 %s " + COMMON_SUFFIX;
    private static final String ONE_HOUR_FORMAT = "잠시후 %s %s %s에서 %s " + COMMON_SUFFIX;

    private static final String RECRUIT_PREFIX = "이번주 테니스파크 활동 추가모집합니다. ";
    private static final String RECRUIT_FORMAT = "%s %s %d석";
    private static final String DELIMITER = ", ";

    private static final String APPROVE_MESSAGE_SUFFIX = " 활동 신청이 승인되었습니다.";
    private static final String CANCEL_MESSAGE_SUFFIX = " 활동 신청이 거절되었습니다.";
    private static final String WAITING_MESSAGE_SUFFIX = " 활동 신청이 대기 상태로 변경되었습니다.";

    private static final String COMMUNITY_COMMENT_MESSAGE = "%s님이 [%s] 게시물에 댓글을 작성했습니다.";

    public static String applicationStatusMessage(ActivityApplication app) {
        String head = ActivityHeaderFormat.header(app.getActivity());
        String suffix = switch (app.getApplicationStatus()) {
            case APPROVED -> APPROVE_MESSAGE_SUFFIX;
            case CANCELED -> CANCEL_MESSAGE_SUFFIX;
            case WAITING -> WAITING_MESSAGE_SUFFIX;
            case PENDING -> "";
        };
        return head + suffix;
    }

    public static String reminderMessage(NotificationType type,
                                         Activity activity,
                                         List<String> participantNames) {
        String names = joinNames(participantNames);
        String place = activity.getPlace().getName();
        String court = activity.getCourtName();

        // beginAt(LocalTime) 기준으로 분까지 한글 포맷
        LocalTime begin = activity.getScheduledTime().getBeginAt();
        String timeText = formatKoreanTime(begin);

        return switch (type) {
            case ONE_DAY_BEFORE -> String.format(ONE_DAY_FORMAT, timeText, place, court, names);
            case ONE_HOUR_BEFORE -> String.format(ONE_HOUR_FORMAT, timeText, place, court, names);
            case RECRUIT_REMINDER -> recruitSingle(activity);
        };
    }

    public static String recruitReminderMessage(List<Activity> activities) {
        StringJoiner sj = new StringJoiner(DELIMITER, RECRUIT_PREFIX, "");
        for (Activity a : activities) {
            sj.add(recruitLine(a));
        }
        return sj.toString();
    }

    private static String recruitSingle(Activity a) {
        return RECRUIT_PREFIX + recruitLine(a);
    }

    private static String recruitLine(Activity a) {
        String place = a.getPlace().getName();
        String court = a.getCourtName();
        int remaining = a.getCapacity() - a.getParticipantCount();
        return String.format(RECRUIT_FORMAT, place, court, remaining);
    }

    private static String joinNames(List<String> names) {
        return String.join(DELIMITER, names);
    }

    public static String communityCommentCreatedMessage(String commenterName, String postTitle) {
        return String.format(COMMUNITY_COMMENT_MESSAGE, commenterName, postTitle);
    }

    // ★ 핵심: 정시는 "20시", 분이 있으면 "20시 30분으로 분까지 표시"
    private static String formatKoreanTime(LocalTime t) {
        int h = t.getHour();
        int m = t.getMinute();
        return (m == 0) ? String.format("%02d시", h) : String.format("%02d시 %02d분", h, m);
    }
}
