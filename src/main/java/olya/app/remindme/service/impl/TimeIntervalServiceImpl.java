package olya.app.remindme.service.impl;

import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.ActionRequestDto;
import olya.app.remindme.model.TimeInterval;
import olya.app.remindme.repository.TimeIntervalRepository;
import olya.app.remindme.service.TimeIntervalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimeIntervalServiceImpl implements TimeIntervalService {
    private final TimeIntervalRepository timeIntervalRepository;

    @Override
    @Transactional
    public TimeInterval create(ActionRequestDto.TimeIntervalDto timeIntervalDto) {
        TimeInterval interval = new TimeInterval();
        interval.setTimeUnit(timeIntervalDto.getTimeUnit());
        interval.setQuantity(timeIntervalDto.getQuantity());
        return timeIntervalRepository.save(interval);
    }

    @Override
    @Transactional
    public TimeInterval update(Long id, ActionRequestDto.TimeIntervalDto timeIntervalDto) {
        TimeInterval interval = timeIntervalRepository.getReferenceById(id);
        interval.setTimeUnit(timeIntervalDto.getTimeUnit());
        interval.setQuantity(timeIntervalDto.getQuantity());
        return timeIntervalRepository.save(interval);
    }
}
