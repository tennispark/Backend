package kr.tennispark.act.admin.application;

import kr.tennispark.act.admin.presentation.dto.request.ManageActRequestDTO;
import kr.tennispark.act.admin.presentation.dto.response.GetActResponseDTO;

public interface ActAdminUseCase {

    void registerAct(ManageActRequestDTO request);

    void modifyActDetails(Long actId, ManageActRequestDTO requestDTO);

    GetActResponseDTO getActList(Integer page, Integer size);

}
