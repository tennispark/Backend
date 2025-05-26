package kr.tennispark.event.application;

import kr.tennispark.event.presentation.dto.request.ManageEventRequestDTO;
import kr.tennispark.event.presentation.dto.response.GetEventResponseDTO;

public interface EventAdminUseCase {

    GetEventResponseDTO getAllEvents(int page, int size);

    void registerEvent(ManageEventRequestDTO request);

    void modifyEventDetails(Long eventId, ManageEventRequestDTO request);

    void removeEvent(Long eventId);


}
