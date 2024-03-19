package olya.app.remindme.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.response.ActionResponseDto;
import olya.app.remindme.dto.response.EventResponseDto;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Event;
import olya.app.remindme.model.User;
import olya.app.remindme.service.ActionService;
import olya.app.remindme.service.EventService;
import olya.app.remindme.service.mapper.ActionMapper;
import olya.app.remindme.service.mapper.EventMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping("/{id}")
    EventResponseDto get (@AuthenticationPrincipal User user, @PathVariable Long id) {
        Event event = eventService.getById(user.getId(), id);
        return eventMapper.mapToDto(event);
    }

    @GetMapping
    List<EventResponseDto> get (@AuthenticationPrincipal User user) {
        List<Event> events = eventService.getAll(user.getId());
        return events.stream().map(eventMapper::mapToDto).collect(Collectors.toList());
    }
}
