package kr.tennispark.activity.user.presentation.dto.response;

import java.time.LocalDate;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.ActivityInfo;
import kr.tennispark.common.utils.KoreanFormat;
import org.springframework.data.domain.Page;

public record GetMyApplicationsResponse(
        List<ApplicationDto> applications
) {
    public static GetMyApplicationsResponse of(Page<ActivityApplication> page) {
        return new GetMyApplicationsResponse(
                page.getContent().stream()
                        .map(ApplicationDto::from)
                        .toList()
        );
    }

    public record ApplicationDto(
            Long id,
            LocalDate applicationDate,
            String applicationStatus,
            ActivityDto activity
    ) {
        public static ApplicationDto from(ActivityApplication aa) {
            Activity a = aa.getActivity();
            ActivityInfo t = a.getTemplate();

            return new ApplicationDto(
                    aa.getId(),
                    aa.getCreatedAt().toLocalDate(),
                    aa.getApplicationStatus().name(),
                    new ActivityDto(
                            KoreanFormat.date(a.getDate()),
                            KoreanFormat.time(t.getTime().getBeginAt()),
                            KoreanFormat.time(t.getTime().getEndAt()),
                            t.getPlace().getName(),
                            a.getActivityName().name()
                    )
            );
        }
    }

    public record ActivityDto(
            String date,
            String startAt,
            String endAt,
            String place,
            String courtType
    ) {
    }
}

