package kr.tennispark.activity.admin.application;

import kr.tennispark.activity.admin.presentation.dto.request.ManageActivityRequestDTO;
import kr.tennispark.activity.admin.presentation.dto.response.GetActivityResponseDTO;

public interface ActivityAdminUseCase {

    void registerActivity(ManageActivityRequestDTO request);

    void modifyActivityDetails(Long activityId, ManageActivityRequestDTO requestDTO);

    GetActivityResponseDTO getActivityList(Integer page, Integer size);

}
