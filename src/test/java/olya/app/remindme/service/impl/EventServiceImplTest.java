package olya.app.remindme.service.impl;

import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Event;
import olya.app.remindme.model.Subject;
import olya.app.remindme.repository.EventRepository;
import olya.app.remindme.service.ActionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
//    @Mock
//    private EventRepository eventRepository;
//
//    @Mock
//    private ActionService actionService;
//
//    @InjectMocks
//    private EventServiceImpl eventService;
//
//    private Action mockAction;
//    private Event mockEvent;
//
//    @BeforeEach
//    void setUp() {
//        mockAction = new Action();
//        mockAction.setId(1L);
//        mockAction.setTitle("Test Action");
//
//        mockEvent = new Event();
//        mockEvent.setId(1L);
//        mockEvent.setAction(mockAction);
//        mockEvent.setDate(LocalDate.now());
//        mockEvent.setStatus(Event.Status.NA);
//    }
//
//    @Test
//    void testCreate_Success() {
//        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);
//
//        Event result = eventService.create(mockAction);
//
//        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
//        verify(eventRepository).save(captor.capture());
//        Event capturedEvent = captor.getValue();
//
//        // step1: captured value vs input/dto
//        assertThat(capturedEvent.getAction().getId()).isEqualTo(1L);
//        assertThat(capturedEvent.getStatus()).isEqualTo(Event.Status.NA);
//        // step 2: result vs mock
//        assertThat(result).usingRecursiveComparison().isEqualTo(mockEvent);
//    }
//
//    @Test
//    void testUpdateEvent_StatusOnly() {
//        Long eventId = 1L;
//        Event.Status newStatus = Event.Status.DONE;
//        // when -> return mocks
//        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEvent));
//        when(eventRepository.save(any(Event.class))).thenReturn(mockEvent);
//        // acting
//        Event result = eventService.update(eventId, newStatus, false);
//
//        // ensure the repository methods are called + capture the value
//        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
//        verify(eventRepository).save(captor.capture());
//        Event capturedEvent = captor.getValue();
//
//        // step1: captured value vs input/dto
//        assertThat(capturedEvent.getId()).isEqualTo(1L);
//        assertThat(capturedEvent.getStatus()).isEqualTo(newStatus);
//        // step 2: result vs updated mock
//        assertThat(result).usingRecursiveComparison().isEqualTo(updatedEvent);
//        verifyNoInteractions(actionService);
//
//    }
//
//    @Test
//    void testUpdateEvent_WithDateUpdate() {
//        Long eventId = 1L;
//        Event.Status newStatus = Event.Status.DONE;
//        LocalDate today = LocalDate.now();
//
//        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
//        when(eventRepository.save(event)).thenReturn(event);
//
//        Event updatedEvent = eventService.update(eventId, newStatus, true);
//
//        assertNotNull(updatedEvent);
//        assertEquals(newStatus, updatedEvent.getStatus());
//        assertEquals(today, updatedEvent.getDate());
//        verify(actionService).updateLastExecutionDate(action, today);
//        verify(eventRepository).save(event);
//    }
//
//    @Test
//    void testUpdateEvent_NotFound() {
//        Long eventId = 999L;
//        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> eventService.update(eventId, Event.Status.DONE, false))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessageContaining("The event with id 999 not found");
//    }
//
//    @Test
//    void testGetById_Success() {
//        Long userId = 1L;
//        Long eventId = 1L;
//
//        when(eventRepository.findEventByUserAndId(userId, eventId)).thenReturn(Optional.of(event));
//
//        Event foundEvent = eventService.getById(userId, eventId);
//
//        assertNotNull(foundEvent);
//        assertEquals(eventId, foundEvent.getId());
//        verify(eventRepository).findEventByUserAndId(userId, eventId);
//    }
//
//    @Test
//    void testGetById_NotFound() {
//        Long userId = 1L;
//        Long eventId = 999L;
//
//        when(eventRepository.findEventByUserAndId(userId, eventId)).thenReturn(Optional.empty());
//
//        assertThrows(EntityNotFoundException.class, () -> eventService.getById(userId, eventId));
//    }
//
//    @Test
//    void testGetByAction_Success() {
//        Long userId = 1L;
//        Long actionId = 1L;
//        List<Event> events = List.of(event);
//
//        when(actionService.getById(userId, actionId)).thenReturn(action);
//        when(eventRepository.findByActionId(actionId)).thenReturn(events);
//
//        List<Event> result = eventService.getByAction(userId, actionId);
//
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        verify(actionService).getById(userId, actionId);
//        verify(eventRepository).findByActionId(actionId);
//    }
//
//    @Test
//    void testGetAllEvents() {
//        Long userId = 1L;
//        List<Event> events = List.of(event);
//
//        when(eventRepository.findEventsByUser(userId)).thenReturn(events);
//
//        List<Event> result = eventService.getAll(userId);
//
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        verify(eventRepository).findEventsByUser(userId);
//    }

}