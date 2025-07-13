package kr.tennispark.activity.admin.presentation.dto.response;

import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;
import org.springframework.data.domain.Page;

public record GetActivityApplicantResponseDTO(Page<ActivityApplicantDTO> applicants) {
    public static GetActivityApplicantResponseDTO of(Page<ActivityApplication> applicants) {
        Page<ActivityApplicantDTO> applicantDTOs = applicants.map(applicant ->
                ActivityApplicantDTO.of(
                        applicant.getMember().getId(),
                        applicant.getMember().getName(),
                        applicant.getMember().getPhone().getNumber(),
                        applicant.getMember().getMemberShipType(),
                        applicant.getApplicationStatus()
                )
        );
        return new GetActivityApplicantResponseDTO(applicantDTOs);
    }


    public record ActivityApplicantDTO(
            Long id,
            String name,
            String phoneNumber,
            MemberShipType membershipType,
            ApplicationStatus applicationStatus
    ) {
        public static ActivityApplicantDTO of(
                Long id,
                String name,
                String phoneNumber,
                MemberShipType membershipType,
                ApplicationStatus applicationStatus
        ) {
            return new ActivityApplicantDTO(id, name, phoneNumber, membershipType, applicationStatus);
        }
    }
}
