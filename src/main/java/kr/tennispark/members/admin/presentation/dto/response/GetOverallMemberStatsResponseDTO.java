package kr.tennispark.members.admin.presentation.dto.response;

public record GetOverallMemberStatsResponseDTO(
        long totalMembers,
        long totalActivityCount,
        String topScorer,
        int topScore
) {
    public static GetOverallMemberStatsResponseDTO of(
            long totalMembers,
            long totalActivityCount,
            String topScorer,
            int topScore
    ) {
        return new GetOverallMemberStatsResponseDTO(totalMembers, totalActivityCount, topScorer, topScore);
    }
}
