package kr.tennispark.members.user.presentation.dto.response;

public record GetMemberMatchRecordResponse(
        long wins,
        long draws,
        long losses,
        int matchPoint,
        long ranking
) {
}
