package kr.tennispark.event.application;

import kr.tennispark.event.presentation.dto.request.ManageEventRequestDTO;

public interface EventAdminUseCase {

    void registerEvent(ManageEventRequestDTO request);

    void modifyEventDetails(Long eventId, ManageEventRequestDTO request);


}
