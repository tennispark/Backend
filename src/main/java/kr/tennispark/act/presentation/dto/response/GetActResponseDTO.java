package kr.tennispark.act.presentation.dto.response;

import java.time.LocalTime;
import kr.tennispark.act.domain.Act;
import org.springframework.data.domain.Page;

public record GetActResponseDTO(Page<ActDetails> acts) {

    public static GetActResponseDTO of(Page<Act> acts) {

        Page<ActDetails> actDetailsList = acts.map(act ->
                ActDetails.of(
                        act.getId(),
                        act.getCourtName(),
                        act.getActTime().getBeginAt(),
                        act.getActTime().getEndAt()
                )
        );

        return new GetActResponseDTO(actDetailsList);
    }

    public record ActDetails(
            Long actId,
            String courtName,
            LocalTime beginAt,
            LocalTime endAt
    ) {

        public static ActDetails of(Long actId, String courtName, LocalTime beginAt, LocalTime endAt) {
            return new ActDetails(actId, courtName, beginAt, endAt);
        }
    }
}
