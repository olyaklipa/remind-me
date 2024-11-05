package olya.app.remindme.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.ActionRequestDto;
import olya.app.remindme.exception.CustomRequestException;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.exception.ExistedEntityException;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Subject;
import olya.app.remindme.model.TimeInterval;
import olya.app.remindme.repository.ActionRepository;
import olya.app.remindme.repository.TimeIntervalRepository;
import olya.app.remindme.service.ActionService;
import olya.app.remindme.service.SubjectService;
import olya.app.remindme.service.TimeIntervalService;
import olya.app.remindme.service.mapper.ActionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final SubjectService subjectService;
    private final TimeIntervalService timeIntervalService;

    @Override
    @Transactional
    public Action create(Long userId, ActionRequestDto actionRequestDto) {
        validateNumOfDaysBeforeEvent(actionRequestDto);
        validateStartDateForCreate(actionRequestDto.getStartDate());
        Subject subject = subjectService.getById(userId, actionRequestDto.getSubjectId());
        if(existedActionTitleForSubject(actionRequestDto.getSubjectId(), actionRequestDto.getTitle())) {
            throw new ExistedEntityException("The action with title " + actionRequestDto.getTitle() +
                    " already exists for the subject " + actionRequestDto.getSubjectId());
        }
        Action action = new Action();
        action.setSubject(subject);
        action.setActive(true);
        TimeInterval savedInterval = timeIntervalService.create(actionRequestDto.getIntervalBetweenEventsDto());
        action.setIntervalBetweenEvents(savedInterval);
        action.setLastExecutionDate(null);
        action.setRepeatsCount(0);
        return actionRepository.save(actionMapper.mapToEntity(action, actionRequestDto));
    }

    @Override
    @Transactional
    public Action update(Long userId, Long id, ActionRequestDto actionRequestDto) {
        validateNumOfDaysBeforeEvent(actionRequestDto);
        Subject subject = subjectService.getById(userId, actionRequestDto.getSubjectId());
        Action action = getById(userId, id);
        validateStartDateForUpdate(action, actionRequestDto.getStartDate());
        if((action.getTitle().equals(actionRequestDto.getTitle())) ||
                (!existedActionTitleForSubject(actionRequestDto.getSubjectId(), actionRequestDto.getTitle()))) {
            action.setSubject(subject);
            TimeInterval updatedInterval = timeIntervalService.update(action.getIntervalBetweenEvents().getId(),
                    actionRequestDto.getIntervalBetweenEventsDto());
            action.setIntervalBetweenEvents(updatedInterval);
            return actionRepository.save(actionMapper.mapToEntity(action, actionRequestDto));
        } else {
            throw new ExistedEntityException("The action with title" + actionRequestDto.getTitle() +
                    " already exists for the subject " + actionRequestDto.getSubjectId());
        }
    }

    @Override
    @Transactional
    public void updateLastExecutionDate(Action action, LocalDate date) {
        action.setLastExecutionDate(date);
        actionRepository.save(action);
    }

    @Override
    @Transactional
    public Action suspend(Long userId, Long id) {
        Action action = getById(userId, id);
        if (!action.isActive()) {
            return action;
        } else {
            action.setActive(false);
            return actionRepository.save(action);
        }
    }

    @Override
    @Transactional
    public Action resume(Long userId, Long id, LocalDate newStartDate) {
        Action action = getById(userId, id);
        if (action.isActive()) {
            return action;
        } else {
            validateStartDateForCreate(newStartDate);
            action.setActive(true);
            action.setLastExecutionDate(null);
            action.setStartDate(newStartDate);
            if (action.getRepeatsCount() >= action.getNumOfRepeats()) {
                action.setRepeatsCount(0);
            }
            return actionRepository.save(action);
        }
    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        actionRepository.delete(getById(userId, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Action getById(Long userId, Long id) {
        return actionRepository.findActionByUserAndId(userId, id)
                .orElseThrow(() -> new EntityNotFoundException("The action with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Action> getBySubject(Long userId, Long subjectId) {
        subjectService.getById(userId, subjectId);
        return actionRepository.findBySubjectId(subjectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Action> getAll(Long userId) {
        return actionRepository.findActionsByUser(userId);
    }

    private boolean existedActionTitleForSubject(Long subjectId, String title) {
        return actionRepository.findBySubjectIdAndTitle(subjectId, title).isPresent();
    }

    private void validateNumOfDaysBeforeEvent (ActionRequestDto actionRequestDto) {
        if (actionRequestDto.getNumOfDaysBeforeEventForAdvanceNotice() != null
                && actionRequestDto.getNumOfDaysBeforeEventForShortNotice() != null
                && actionRequestDto.getNumOfDaysBeforeEventForAdvanceNotice() <= actionRequestDto.getNumOfDaysBeforeEventForShortNotice()) {
            throw new CustomRequestException("The advance notice should be larger than short one");
        }
    }

    private void validateStartDateForCreate (LocalDate startDate) {
        if (!startDate.isAfter(LocalDate.now())) {
            throw new CustomRequestException("The startDate has already passed");
        }
    }

    private void validateStartDateForUpdate (Action action, LocalDate newStartDate) {
        if (!newStartDate.isEqual(action.getStartDate())) {
            if (action.getLastExecutionDate() != null) {
                throw new CustomRequestException("The action cannot be updated with a new startDate");
            } else if (!newStartDate.isAfter(LocalDate.now())) {
                throw new CustomRequestException("The new startDate has already passed");
            }
        }
    }
}
