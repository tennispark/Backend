package kr.tennispark.activity.user.application.service;

import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.user.infrastructure.repository.UserActivityApplicationRepository;
import kr.tennispark.activity.user.presentation.dto.response.GetMyApplicationsResponse;
import kr.tennispark.members.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityApplicationQueryService {
    private final UserActivityApplicationRepository activityApplicationRepository;

    public GetMyApplicationsResponse getMyApplications(Member member, int page, int size) {
        Page<ActivityApplication> result = activityApplicationRepository.findMyApplications(
                member.getId(),
                PageRequest.of(page, size)
        );
        return GetMyApplicationsResponse.of(result);
    }
}
