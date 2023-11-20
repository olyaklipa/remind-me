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
@RequestMapping("/actions")
public class ActionController {
    private final ActionService actionService;
    private final ActionMapper actionMapper;
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    ActionResponseDto create (@AuthenticationPrincipal User user,
                              @Valid @RequestBody ActionRequestDto actionRequestDto) {
        Action action = actionService.create(user.getId(), actionRequestDto);
        return actionMapper.mapToDto(action);
    }

    @PutMapping("/{id}")
    ActionResponseDto update (@AuthenticationPrincipal User user,
                              @PathVariable Long id,
                              @Valid @RequestBody ActionRequestDto actionRequestDto) {
        Action action = actionService.update(user.getId(), id, actionRequestDto);
        return actionMapper.mapToDto(action);
    }

//    @PutMapping("/suspend/{id}")
//    ActionResponseDto suspend (@AuthenticationPrincipal User user,
//                               @PathVariable Long id) {
//        Action action = actionService.suspend(user.getId(), id);
//        return actionMapper.mapToDto(action);
//    }
//
//    @PutMapping("/reactivate/{id}")
//    ActionResponseDto reactivate (@AuthenticationPrincipal User user,
//                                  @PathVariable Long id) {
//        Action action = actionService.reactivate(user.getId(), id);
//        return actionMapper.mapToDto(action);
//    }

    @DeleteMapping("/{id}")
    void delete (@AuthenticationPrincipal User user,
                 @PathVariable Long id) {
        actionService.delete(user.getId(), id);
    }

    @GetMapping("/{id}")
    ActionResponseDto get (@AuthenticationPrincipal User user, @PathVariable Long id) {
        Action action = actionService.getById(user.getId(), id);
        return actionMapper.mapToDto(action);
    }

    @GetMapping
    List<ActionResponseDto> get (@AuthenticationPrincipal User user) {
        List<Action> actions = actionService.getAll(user.getId());
        return actions.stream().map(actionMapper::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}/events")
    List<EventResponseDto> getEvents (@AuthenticationPrincipal User user, @PathVariable Long id) {
        List<Event> events = eventService.getByAction(user.getId(), id);
        return events.stream().map(eventMapper::mapToDto).collect(Collectors.toList());
    }

}
