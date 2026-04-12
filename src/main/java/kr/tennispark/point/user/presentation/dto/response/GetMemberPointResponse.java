package kr.tennispark.point.user.presentation.dto.response;

public record GetMemberPointResponse(
        int points,
        Double coupon
) {
    public static GetMemberPointResponse of(int points, Double coupon) {
        return new GetMemberPointResponse(points, coupon);
    }
}
