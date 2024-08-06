package olya.app.remindme.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.ActionRequestDto;
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
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}/status")
    EventResponseDto update (@PathVariable Long id,
                             @RequestParam Event.Status status) {
        Event event = eventService.updateStatus(id, status);
        return eventMapper.mapToDto(event);
    }
}
