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
        actionResponseDto.setFrequency(action.getFrequency());
        actionResponseDto.setRepeats(action.getRepeats());
        actionResponseDto.setNoticePeriodAdvance(action.getNoticePeriodAdvance());
        actionResponseDto.setNoticePeriodShort(action.getNoticePeriodShort());
        actionResponseDto.setNotificationMethod(action.getNotificationMethod());
        actionResponseDto.setRequiresConfirmation(action.isRequiresConfirmation());
        return actionResponseDto;
    }

    public Action mapToEntity(Action action, ActionRequestDto actionRequestDto) {
        action.setTitle(actionRequestDto.getTitle());
        action.setStartDate(actionRequestDto.getStartDate());
        action.setFrequency(actionRequestDto.getFrequency());
        action.setRepeats(actionRequestDto.getRepeats());
        action.setNoticePeriodAdvance(actionRequestDto.getNoticePeriodAdvance());
        action.setNoticePeriodShort(actionRequestDto.getNoticePeriodShort());
        action.setNotificationMethod(actionRequestDto.getNotificationMethod());
        action.setRequiresConfirmation(actionRequestDto.isRequiresConfirmation());
        return action;
    }

}
