package kr.tennispark.members.admin.presentation.dto.response;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.Gender;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;

public record GetTopMembersResponseDTO(
        List<TopMemberDTO> members
) {

    public static GetTopMembersResponseDTO of(List<Member> members) {
        AtomicInteger rank = new AtomicInteger(1);
        List<TopMemberDTO> dtos = members.stream()
                .map(member -> TopMemberDTO.of(member, rank.getAndIncrement()))
                .toList();
        return new GetTopMembersResponseDTO(dtos);
    }

    public record TopMemberDTO(
            int ranking,
            String phoneNumber,
            Gender gender,
            int experience,
            int matchPoint,
            String instagramId,
            MemberShipType type
    ) {
        public static TopMemberDTO of(Member member, int ranking) {
            return new TopMemberDTO(
                    ranking,
                    member.getPhone().getNumber(),
                    member.getGender(),
                    member.getTennisCareer(),
                    member.getMatchPoint(),
                    member.getInstagramId(),
                    member.getMemberShipType()
            );
        }
    }
}