package olya.app.remindme.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.SubjectRequestDto;
import olya.app.remindme.dto.response.ActionResponseDto;
import olya.app.remindme.dto.response.SubjectResponseDto;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Subject;
import olya.app.remindme.model.User;
import olya.app.remindme.service.ActionService;
import olya.app.remindme.service.SubjectService;
import olya.app.remindme.service.mapper.ActionMapper;
import olya.app.remindme.service.mapper.SubjectMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;
    private final ActionService actionService;
    private final ActionMapper actionMapper;

    @PostMapping
    SubjectResponseDto create (@AuthenticationPrincipal User user,
                               @Valid @RequestBody SubjectRequestDto subjectRequestDto) {
        Subject subject = subjectService.create(user.getId(), subjectRequestDto);
        return subjectMapper.mapToDto(subject);
    }

    @PutMapping("/{id}")
    SubjectResponseDto update (@AuthenticationPrincipal User user,
                               @PathVariable Long id,
                               @Valid @RequestBody SubjectRequestDto subjectRequestDto) {
        Subject subject = subjectService.update(user.getId(), id, subjectRequestDto);
        return subjectMapper.mapToDto(subject);
    }

    @DeleteMapping("/{id}")
    void delete (@AuthenticationPrincipal User user, @PathVariable Long id) {
        subjectService.delete(user.getId(), id);
    }

    @GetMapping("/{id}")
    SubjectResponseDto get (@AuthenticationPrincipal User user, @PathVariable Long id) {
        Subject subject = subjectService.getById(user.getId(), id);
        return subjectMapper.mapToDto(subject);
    }

    @GetMapping
    List<SubjectResponseDto> get (@AuthenticationPrincipal User user) {
        List<Subject> subjects = subjectService.getAll(user.getId());
        return subjects.stream().map(subjectMapper::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}/actions")
    List<ActionResponseDto> getActions (@AuthenticationPrincipal User user, @PathVariable Long id) {
        List<Action> actions = actionService.getBySubject(user.getId(), id);
        return actions.stream().map(actionMapper::mapToDto).collect(Collectors.toList());
    }
}
