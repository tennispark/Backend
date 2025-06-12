package kr.tennispark.members.user.presentation.dto.response;

public record GetMemberMatchRecordResponse(
        long wins,
        long draws,
        long losses,
        int matchPoint,
        long ranking
) {
    public static GetMemberMatchRecordResponse of(
            long wins,
            long draws,
            long losses,
            int matchPoint,
            long ranking
    ) {
        return new GetMemberMatchRecordResponse(wins, draws, losses, matchPoint, ranking);
    }
}
