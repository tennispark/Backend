package kr.tennispark.match.admin.application;

import kr.tennispark.match.admin.presentation.dto.request.SaveMatchResultRequestDTO;
import kr.tennispark.match.admin.presentation.dto.response.GetMemberSummaryResponseDTO;

public interface MatchResultService {
    void saveMatchResult(SaveMatchResultRequestDTO request);

    GetMemberSummaryResponseDTO searchMemberNameForMatchResult(String memberName);

}
