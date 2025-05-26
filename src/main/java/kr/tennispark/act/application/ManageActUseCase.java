package kr.tennispark.act.application;

import kr.tennispark.act.presentation.dto.request.ManageActRequestDTO;
import kr.tennispark.act.presentation.dto.response.GetActResponseDTO;

public interface ManageActUseCase {

    void registerAct(ManageActRequestDTO request);

    void modifyActDetails(Long actId, ManageActRequestDTO requestDTO);

    GetActResponseDTO getActList(Integer page, Integer size);

}
