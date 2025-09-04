package kr.tennispark.web.member.presentation.dto;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.Gender;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;

public record MemberManagementRowDTO(
        Long id,
        String name,
        String gender,
        Integer career,
        Integer score,
        long rank,
        MemberShipType type,
        Double coupon
) {
    public static MemberManagementRowDTO of(Member m, long rank) {
        return new MemberManagementRowDTO(
                m.getId(),
                m.getName(),
                mapGender(m.getGender()),
                m.getTennisCareer(),
                m.getMatchPoint(),
                rank,
                m.getMemberShipType(),
                formatCoupon(m.getCoupon())
        );
    }

    private static String mapGender(Gender g) {
        if (g == null) {
            return "기타";
        }
        try {
            return switch (g) {
                case MAN -> "남";
                case WOMAN -> "여";
            };
        } catch (Exception e) {
            return g.toString();
        }
    }

    private static Double formatCoupon(Double coupon) {
        if (coupon == null) {
            return 0.0;
        }
        return Math.round(coupon * 10) / 10.0;
    }
}
