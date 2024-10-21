package olya.app.remindme.service;

import java.time.LocalDate;
import olya.app.remindme.model.Action;

public interface CommunicationService {
    void sendData(Action action, LocalDate date, String template, String eventId);
}
