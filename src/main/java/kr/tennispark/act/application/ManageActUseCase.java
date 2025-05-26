package kr.tennispark.act.application;

import kr.tennispark.act.presentation.dto.request.ManageActRequestDTO;

public interface ManageActUseCase {

    void registerAct(ManageActRequestDTO request);

    void modifyActDetails(Long actId, ManageActRequestDTO requestDTO);

}
