package olya.app.remindme.service.mapper;

import olya.app.remindme.dto.request.ActionRequestDto;
import olya.app.remindme.dto.response.ActionResponseDto;
import olya.app.remindme.model.Action;
import org.springframework.stereotype.Component;

@Component
public class ActionMapper {
    public ActionResponseDto mapToDto(Action action) {
        ActionResponseDto actionResponseDto = new ActionResponseDto();
        actionResponseDto.setId(action.getId());
        actionResponseDto.setSubjectId(action.getSubject().getId());
        actionResponseDto.setTitle(action.getTitle());
        actionResponseDto.setActive(action.isActive());
        actionResponseDto.setStartDate(action.getStartDate());
        actionResponseDto.setRepeatEveryNumOfDays(action.getRepeatEveryNumOfDays());
        actionResponseDto.setNumOfRepeats(action.getNumOfRepeats());
        actionResponseDto.setNumOfDaysBeforeEventForAdvanceNotice(action.getNumOfDaysBeforeEventForAdvanceNotice());
        actionResponseDto.setNumOfDaysBeforeEventForShortNotice(action.getNumOfDaysBeforeEventForShortNotice());
        actionResponseDto.setNotificationMethod(action.getNotificationMethod());
        actionResponseDto.setRequiresConfirmation(action.isRequiresConfirmation());
        return actionResponseDto;
    }

    public Action mapToEntity(Action action, ActionRequestDto actionRequestDto) {
        action.setTitle(actionRequestDto.getTitle());
        action.setActive(actionRequestDto.isActive());
        action.setStartDate(actionRequestDto.getStartDate());
        action.setRepeatEveryNumOfDays(actionRequestDto.getRepeatEveryNumOfDays());
        action.setNumOfRepeats(actionRequestDto.getNumOfRepeats());
        action.setNumOfDaysBeforeEventForAdvanceNotice(actionRequestDto.getNumOfDaysBeforeEventForAdvanceNotice());
        action.setNumOfDaysBeforeEventForShortNotice(actionRequestDto.getNumOfDaysBeforeEventForShortNotice());
        action.setNotificationMethod(actionRequestDto.getNotificationMethod());
        action.setRequiresConfirmation(actionRequestDto.isRequiresConfirmation());
        return action;
    }

}
