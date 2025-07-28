package kr.tennispark.members.user.presentation.dto.response;

public record GetMemberMatchRecordResponse(
        long wins,
        long draws,
        long losses,
        int matchPoint,
        long ranking,
        double coupon
) {
    public static GetMemberMatchRecordResponse of(
            long wins,
            long draws,
            long losses,
            int matchPoint,
            long ranking,
            double coupon

    ) {
        return new GetMemberMatchRecordResponse(wins, draws, losses, matchPoint, ranking, coupon);
    }
}
