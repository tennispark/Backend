package kr.tennispark.event.admin.application;

import kr.tennispark.event.admin.presentation.dto.request.ManageEventRequestDTO;
import kr.tennispark.event.admin.presentation.dto.response.GetEventResponseDTO;

public interface EventAdminUseCase {

    GetEventResponseDTO getAllEvents(int page, int size);

    void registerEvent(ManageEventRequestDTO request);

    void modifyEventDetails(Long eventId, ManageEventRequestDTO request);

    void removeEvent(Long eventId);


}
