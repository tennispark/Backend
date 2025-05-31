package kr.tennispark.activity.admin.application;

import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityInfoRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityApplicationResponseDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseInfoDTO;

public interface ActivityAdminUseCase {

    void registerActivityInfo(ManageActivityInfoRequestDTO request);

    void modifyActivityInfoDetails(Long activityId, ManageActivityInfoRequestDTO requestDTO);

    GetActivityResponseInfoDTO getActivityInfoList(Integer page, Integer size);

    GetActivityApplicationResponseDTO getActivityApplicationList(Integer page, Integer size);

}
