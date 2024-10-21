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
import olya.app.remindme.repository.ActionRepository;
import olya.app.remindme.service.ActionService;
import olya.app.remindme.service.SubjectService;
import olya.app.remindme.service.mapper.ActionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final SubjectService subjectService;

    @Override
    @Transactional
    public Action create(Long userId, ActionRequestDto actionRequestDto) {
        checkStartDateForCreate(actionRequestDto.getStartDate());
        validateActionRequestDto(actionRequestDto);
        Subject subject = subjectService.getById(userId, actionRequestDto.getSubjectId());
        if(existedActionTitleForSubject(actionRequestDto.getSubjectId(), actionRequestDto.getTitle())) {
            throw new ExistedEntityException("The action with title " + actionRequestDto.getTitle() +
                    " already exists for the subject " + actionRequestDto.getSubjectId());
        }
        Action action = new Action();
        action.setSubject(subject);
        action.setActive(true);
        action.setLastExecutionDate(null);
        action.setRepeatsCount(0);
        return actionRepository.save(actionMapper.mapToEntity(action, actionRequestDto));
    }

    @Override
    @Transactional
    public Action update(Long userId, Long id, ActionRequestDto actionRequestDto) {
        validateActionRequestDto(actionRequestDto);
        Subject subject = subjectService.getById(userId, actionRequestDto.getSubjectId());
        Action action = getById(userId, id);
        checkStartDateForUpdate(action, actionRequestDto.getStartDate());
        if((action.getTitle().equals(actionRequestDto.getTitle())) ||
                (!existedActionTitleForSubject(actionRequestDto.getSubjectId(), actionRequestDto.getTitle()))) {
            action.setSubject(subject);
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

//    @Override
//    public Action suspend(Long userId, Long id) {
//        Action action = getById(userId, id);
//        if (!action.isActive()) {
//            return action;
//        } else {
//            action.setActive(false);
//            return actionRepository.save(action);
//        }
//    }
//
//    @Override
//    public Action reactivate(Long userId, Long id) {
//        Action action = getById(userId, id);
//        if (action.isActive()) {
//            return action;
//        } else {
//            action.setActive(true);
//            return actionRepository.save(action);
//        }
//    }

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

    private void validateActionRequestDto (ActionRequestDto actionRequestDto) {
        if (actionRequestDto.getNumOfDaysBeforeEventForAdvanceNotice() != null
                && actionRequestDto.getNumOfDaysBeforeEventForShortNotice() != null
                && actionRequestDto.getNumOfDaysBeforeEventForAdvanceNotice() <= actionRequestDto.getNumOfDaysBeforeEventForShortNotice()) {
            throw new CustomRequestException("The short notice should have smaller value than advanced one");
        }
        if (actionRequestDto.getNumOfDaysBeforeEventForAdvanceNotice() != null
                && LocalDate.now().plusDays(actionRequestDto.getNumOfDaysBeforeEventForAdvanceNotice()).isAfter(actionRequestDto.getStartDate())) {
            throw new CustomRequestException("The advance notice date has already passed for the startDate provided");
        }
        if (actionRequestDto.getNumOfDaysBeforeEventForShortNotice() != null
                && LocalDate.now().plusDays(actionRequestDto.getNumOfDaysBeforeEventForShortNotice()).isAfter(actionRequestDto.getStartDate())) {
            throw new CustomRequestException("The short notice date has already passed for the startDate provided");
        }
    }

    private void checkStartDateForCreate (LocalDate startDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new CustomRequestException("The startDate has already passed");
        }
    }

    private void checkStartDateForUpdate (Action action, LocalDate newStartDate) {
        if (newStartDate.isEqual(action.getStartDate()) && action.getLastExecutionDate() != null) {
            throw new CustomRequestException("The action cannot be updated with a new startDate");
        }
    }
}
