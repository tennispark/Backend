package kr.tennispark.activity.admin.application;

import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseDTO;

public interface ActivityAdminUseCase {

    void registerActivityInfo(ManageActivityRequestDTO request);

    void modifyActivityInfoDetails(Long activityId, ManageActivityRequestDTO requestDTO);

    GetActivityResponseDTO getActivityInfoList(Integer page, Integer size);

}
