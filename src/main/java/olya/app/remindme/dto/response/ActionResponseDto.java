package olya.app.remindme.dto.response;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Subject;

@Getter
@Setter
public class ActionResponseDto {
    private Long id;
    private Long subjectId;
    private String title;
    private boolean active;
    private LocalDate startDate;
    private int frequency;
    private Integer repeats;
    private int noticePeriodAdvance;
    private int noticePeriodShort;
    private Action.NotificationMethod notificationMethod;
    private boolean requiresConfirmation;
}
