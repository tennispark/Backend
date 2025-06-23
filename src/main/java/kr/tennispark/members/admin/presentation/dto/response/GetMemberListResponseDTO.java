package kr.tennispark.members.admin.presentation.dto.response;

import java.util.List;
import java.util.Map;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.Gender;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;

public record GetMemberListResponseDTO(List<MemberDTO> members) {

    public static GetMemberListResponseDTO of(List<Member> members, Map<Long, Integer> ranks) {
        List<MemberDTO> dtos = members.stream()
                .map(member -> MemberDTO.of(member, ranks.getOrDefault(member.getId(), 0)))
                .toList();
        return new GetMemberListResponseDTO(dtos);
    }

    public record MemberDTO(
            Long memberId,
            String phoneNumber,
            String name,
            Integer tennisCareer,
            Gender gender,
            int matchPoint,
            int ranking,
            MemberShipType memberShipType
    ) {
        public static MemberDTO of(Member member, int ranking) {
            return new MemberDTO(
                    member.getId(),
                    member.getPhone().getNumber(),
                    member.getName(),
                    member.getTennisCareer(),
                    member.getGender(),
                    member.getMatchPoint(),
                    ranking,
                    member.getMemberShipType()
            );
        }
    }
}
