package kr.tennispark.point.user.application.service;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.common.domain.entity.Point;
import kr.tennispark.point.common.domain.entity.PointHistory;
import kr.tennispark.point.user.infrastrurcture.repository.PointHistoryRepository;
import kr.tennispark.point.user.infrastrurcture.repository.PointRepository;
import kr.tennispark.point.user.presentation.dto.response.GetMemberPointHistoryResponse;
import kr.tennispark.point.user.presentation.dto.response.GetMemberPointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPointService {

    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public GetMemberPointResponse getMemberPoint(Member member) {
        Point point = pointRepository.getByMemberId(member.getId());

        // getTotalPoint()가 Double이므로 int로 변환 (소수점 없다는 전제)
        int points = point.getTotalPoint().intValue();

        Double coupon = member.getCoupon();

        return GetMemberPointResponse.of(points, coupon);
    }

    public GetMemberPointHistoryResponse getMemberPointHistory(Member member) {
        List<PointHistory> histories = pointHistoryRepository.findAllByMember(member);
        return GetMemberPointHistoryResponse.of(histories);
    }
}
