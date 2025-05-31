package kr.tennispark.point.user.application.service;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.common.domain.entity.Point;
import kr.tennispark.point.common.domain.entity.PointHistory;
import kr.tennispark.point.common.domain.entity.enums.PointReason;
import kr.tennispark.point.user.application.exception.PointNegativeValueException;
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

    @Transactional
    public void applyPoint(Member member, int points, PointReason reason) {
        if (points < 0) {
            throw new PointNegativeValueException();
        }
        Point point = pointRepository.getByMemberId(member.getId());

        int signedPoint = reason.isEarned() ? Math.abs(points) : -Math.abs(points);

        point.updatePoint(signedPoint);
        pointHistoryRepository.save(PointHistory.of(point, member, points, reason));
    }

    public GetMemberPointResponse getMemberPoint(Member member) {
        Point point = pointRepository.getByMemberId(member.getId());
        return new GetMemberPointResponse(point.getTotalPoint());
    }

    public GetMemberPointHistoryResponse getMemberPointHistory(Member member) {
        List<PointHistory> histories = pointHistoryRepository.findAllByMember(member);
        return GetMemberPointHistoryResponse.of(histories);
    }
}
