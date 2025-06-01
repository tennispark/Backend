package kr.tennispark.members.admin.presentation.dto.response;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;

public record GetMemberListResponseDTO(List<MemberDTO> members) {

    public static GetMemberListResponseDTO of(List<Member> members) {
        List<MemberDTO> memberDTOs = members.stream()
                .map(member -> MemberDTO.of(
                        member.getId(),
                        member.getPhone().getNumber(),
                        member.getName(),
                        member.getTennisCareer(),
                        member.getYear(),
                        member.getInstagramId(),
                        member.getMemberShipType()
                ))
                .toList();
        return new GetMemberListResponseDTO(memberDTOs);
    }

    public record MemberDTO(
            Long memberId,
            String phoneNumber,
            String name,
            String tennisCareer,
            int year,
            String instagramId,
            MemberShipType memberShipType
    ) {
        public static MemberDTO of(Long memberId, String phoneNumber, String name, String tennisCareer, int year,
                                   String instagramId, MemberShipType memberShipType) {
            return new MemberDTO(memberId, phoneNumber, name, tennisCareer, year, instagramId, memberShipType);
        }
    }
}
