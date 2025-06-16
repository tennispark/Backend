package kr.tennispark.activity.admin.application;

import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityApplicationRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityInfoRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityApplicantResponseDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityApplicationResponseDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseInfoDTO;

public interface ActivityAdminUseCase {

    void registerActivityInfo(ManageActivityInfoRequestDTO request);

    void modifyActivityInfoDetails(Long activityInfoId, ManageActivityInfoRequestDTO requestDTO);

    void modifyActivityApplication(Long applicantId, Long activityId, ManageActivityApplicationRequestDTO request);

    void deleteActivityInfo(Long activityInfoId);

    GetActivityResponseInfoDTO getActivityInfoList(Integer page, Integer size);

    GetActivityApplicationResponseDTO getActivityApplicationList(Integer page, Integer size);

    GetActivityApplicantResponseDTO getActivityApplicantList(Long activityId, Integer page, Integer size);

}
