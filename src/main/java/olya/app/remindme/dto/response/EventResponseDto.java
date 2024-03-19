package olya.app.remindme.dto.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Event;

@Getter
@Setter
public class EventResponseDto {
    private Long id;
    private Long actionId;
    private LocalDate date;
    private Event.Status status;

}
