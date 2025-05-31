package kr.tennispark.point.user.application.service;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.common.domain.entity.Point;
import kr.tennispark.point.common.domain.entity.PointHistory;
import kr.tennispark.point.common.domain.entity.enums.PointReason;
import kr.tennispark.point.user.infrastrurcture.repository.PointHistoryRepository;
import kr.tennispark.point.user.infrastrurcture.repository.PointRepository;
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
    public void applyPoint(Member member, int points, PointReason reason){
        Point point = pointRepository.getByMemberId(member.getId());

        point.updatePoint(points);
        pointHistoryRepository.save(PointHistory.of(point, member, points, reason));
    }
}
