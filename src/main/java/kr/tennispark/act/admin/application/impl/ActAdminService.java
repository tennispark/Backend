package kr.tennispark.act.admin.application.impl;

import kr.tennispark.act.admin.application.ActAdminUseCase;
import kr.tennispark.act.admin.infrastructure.repository.ActRepository;
import kr.tennispark.act.admin.presentation.dto.request.ManageActRequestDTO;
import kr.tennispark.act.admin.presentation.dto.response.GetActResponseDTO;
import kr.tennispark.act.common.domain.Act;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActAdminService implements ActAdminUseCase {

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
    }

    @Override
    public GetActResponseDTO getActList(Integer page, Integer size) {
        Page<Act> actPage = actRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        return GetActResponseDTO.of(actPage);
    }
}
