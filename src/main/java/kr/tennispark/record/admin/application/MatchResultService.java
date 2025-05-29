package kr.tennispark.record.admin.application;

import kr.tennispark.record.admin.presentation.dto.request.SaveMatchResultRequestDTO;
import kr.tennispark.record.admin.presentation.dto.response.GetMemberSummaryResponseDTO;

public interface MatchResultService {
    void saveMatchResult(SaveMatchResultRequestDTO request);

    GetMemberSummaryResponseDTO searchMemberNameForMatchResult(String memberName);

}
