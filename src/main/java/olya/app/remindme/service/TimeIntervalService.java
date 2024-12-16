package olya.app.remindme.service;

import olya.app.remindme.dto.request.ActionRequestDto;
import olya.app.remindme.model.TimeInterval;

public interface TimeIntervalService {
    TimeInterval create(ActionRequestDto.TimeIntervalDto timeIntervalDto);
    TimeInterval update(Long id, ActionRequestDto.TimeIntervalDto timeIntervalDto);
}
