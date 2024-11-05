package olya.app.remindme.dto.response;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Subject;
import olya.app.remindme.model.TimeInterval;

@Getter
@Setter
public class ActionResponseDto {
    private Long id;
    private Long subjectId;
    private String title;
    private boolean active;
    private LocalDate startDate;
    private LocalDate lastExecutionDate;
    private TimeInterval intervalBetweenEvents;
    private Integer numOfRepeats;
    private int repeatsCount;
    private Integer numOfDaysBeforeEventForAdvanceNotice;
    private Integer numOfDaysBeforeEventForShortNotice;
    private Action.NotificationMethod notificationMethod;
    private boolean requiresConfirmation;
}
