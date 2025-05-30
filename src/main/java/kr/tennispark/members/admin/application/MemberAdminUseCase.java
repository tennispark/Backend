package kr.tennispark.members.admin.application;

import java.time.LocalDate;
import kr.tennispark.members.admin.presentation.dto.response.GetMemberListResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetMonthlyMemberActivityStatsResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetOverallMemberStatsResponseDTO;

public interface MemberAdminUseCase {

    GetMonthlyMemberActivityStatsResponseDTO getMonthlyActivityStats(LocalDate from, LocalDate to);

    GetOverallMemberStatsResponseDTO getOverallMemberStats();

    GetMemberListResponseDTO getMemberList(String name);
}
