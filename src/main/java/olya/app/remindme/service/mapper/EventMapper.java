package olya.app.remindme.service.mapper;

import olya.app.remindme.dto.response.EventResponseDto;
import olya.app.remindme.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventResponseDto mapToDto(Event event){
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setActionId(event.getAction().getId());
        eventResponseDto.setDate(event.getDate());
        eventResponseDto.setStatus(event.getStatus());
        return eventResponseDto;
    }
}
