package olya.app.remindme.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import olya.app.remindme.dto.request.ActionRequestDto;
import olya.app.remindme.model.TimeInterval;
import olya.app.remindme.repository.TimeIntervalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimeIntervalServiceImplTest {
    @Mock
    private TimeIntervalRepository timeIntervalRepository;

    @InjectMocks
    private TimeIntervalServiceImpl timeIntervalService;

    private ActionRequestDto.TimeIntervalDto mockTimeIntervalDto;
    private TimeInterval mockTimeInterval;

    @BeforeEach
    void setUp() {
        mockTimeIntervalDto = new ActionRequestDto.TimeIntervalDto();
        mockTimeIntervalDto.setTimeUnit(TimeInterval.TimeUnit.DAYS);
        mockTimeIntervalDto.setQuantity(7);

        mockTimeInterval = new TimeInterval();
        mockTimeInterval.setId(1L);
        mockTimeInterval.setTimeUnit(TimeInterval.TimeUnit.DAYS);
        mockTimeInterval.setQuantity(7);
    }

    @Test
    void testCreate_Success() {
        //when -> return mocks
        when(timeIntervalRepository.save(any(TimeInterval.class))).thenReturn(mockTimeInterval);
        // acting
        TimeInterval result = timeIntervalService.create(mockTimeIntervalDto);
        //ensure the repositories methods are called + get captured value
        ArgumentCaptor<TimeInterval> captor = ArgumentCaptor.forClass(TimeInterval.class);
        verify(timeIntervalRepository).save(captor.capture());
        TimeInterval capturedInterval = captor.getValue();
        //step1: dto vs captured value
        assertThat(mockTimeIntervalDto.getTimeUnit()).isEqualTo(capturedInterval.getTimeUnit());
        assertThat(mockTimeIntervalDto.getQuantity()).isEqualTo(capturedInterval.getQuantity());
        //step2: expected vs return value
        assertThat(result).usingRecursiveComparison().isEqualTo(mockTimeInterval);
    }

    @Test
    void testUpdate_Success() {
        TimeInterval mockOriginalTimeInterval = new TimeInterval();
        mockOriginalTimeInterval.setId(1L);
        mockOriginalTimeInterval.setTimeUnit(TimeInterval.TimeUnit.MONTHS);
        mockOriginalTimeInterval.setQuantity(2);

        //when -> return mocks
        when(timeIntervalRepository.getReferenceById(mockOriginalTimeInterval.getId())).thenReturn(mockOriginalTimeInterval);
        when(timeIntervalRepository.save(any(TimeInterval.class))).thenReturn(mockTimeInterval);
        // acting
        TimeInterval result = timeIntervalService.update(mockOriginalTimeInterval.getId(), mockTimeIntervalDto);
        //ensure the repositories methods are called + get captured value
        verify(timeIntervalRepository).getReferenceById(mockOriginalTimeInterval.getId());
        ArgumentCaptor<TimeInterval> captor = ArgumentCaptor.forClass(TimeInterval.class);
        verify(timeIntervalRepository).save(captor.capture());
        TimeInterval capturedInterval = captor.getValue();
        //step1: dto vs captured value
        assertThat(mockTimeIntervalDto.getTimeUnit()).isEqualTo(capturedInterval.getTimeUnit());
        assertThat(mockTimeIntervalDto.getQuantity()).isEqualTo(capturedInterval.getQuantity());
        //step2: expected vs return value
        assertThat(result).usingRecursiveComparison().isEqualTo(mockTimeInterval);
    }
}
