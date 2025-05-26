package kr.tennispark.act.application.impl;

import kr.tennispark.act.application.ManageActUseCase;
import kr.tennispark.act.domain.Act;
import kr.tennispark.act.infrastructure.repository.ActRepository;
import kr.tennispark.act.presentation.dto.request.ManageActRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageActService implements ManageActUseCase {

    private final ActRepository actRepository;

    @Override
    public void registerAct(ManageActRequestDTO request) {
        Act act = Act.of(request.courtName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount());

        actRepository.save(act);
    }

    @Override
    public void modifyActDetails(Long actId, ManageActRequestDTO request) {
        Act act = actRepository.getById(actId);

        act.modifyActDetails(request.courtName(),
                request.address(),
                request.beginAt(),
                request.endAt(),
                request.activeDays(),
                request.isRecurring(),
                request.participantCount());

        actRepository.save(act);
    }
}
