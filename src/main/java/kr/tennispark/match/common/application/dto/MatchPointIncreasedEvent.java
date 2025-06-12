package kr.tennispark.match.common.application.dto;

public record MatchPointIncreasedEvent(
        Long memberId,
        int point) {
}