package kr.tennispark.members.admin.application;

import java.time.LocalDate;
import kr.tennispark.members.admin.presentation.dto.response.GetMemberListResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetMonthlyMemberActivityStatsResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetOverallMemberStatsResponseDTO;
import kr.tennispark.members.admin.presentation.dto.response.GetTopMembersResponseDTO;

public interface MemberAdminUseCase {

    GetMonthlyMemberActivityStatsResponseDTO getMonthlyActivityStats(LocalDate from, LocalDate to);

    GetOverallMemberStatsResponseDTO getOverallMemberStats();

    GetMemberListResponseDTO getMemberList(String name);

    GetTopMembersResponseDTO getTopMembers();
}
