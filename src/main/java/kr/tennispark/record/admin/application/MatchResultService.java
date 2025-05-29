package kr.tennispark.record.admin.application;

import kr.tennispark.record.admin.presentation.dto.request.SaveMatchResultRequestDTO;

public interface MatchResultService {
    void saveMatchResult(SaveMatchResultRequestDTO request);

}
