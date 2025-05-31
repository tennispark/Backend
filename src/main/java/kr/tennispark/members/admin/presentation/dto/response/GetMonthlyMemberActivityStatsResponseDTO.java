package kr.tennispark.members.admin.presentation.dto.response;

public record GetMonthlyMemberActivityStatsResponseDTO(
        int participantCount,
        int totalEarnedPoints,
        String topEarner,
        String topSpender
) {
    public static GetMonthlyMemberActivityStatsResponseDTO of(
            int participantCount,
            int totalEarnedPoints,
            String topEarner,
            String topSpender
    ) {
        return new GetMonthlyMemberActivityStatsResponseDTO(participantCount, totalEarnedPoints, topEarner, topSpender);
    }
}
