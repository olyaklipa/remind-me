package olya.app.remindme.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Subject;

@Getter
public class ActionRequestDto {
    @NotNull(message = "Subject is required")
    private Long subjectId;
    @NotBlank(message = "Title is required")
    private String title;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "Frequency is required")
    private int frequency;
    private Integer repeats;
    private int noticePeriodAdvance;
    private int noticePeriodShort;
    @NotNull(message = "Notification method is required")
    private Action.NotificationMethod notificationMethod;
    private boolean requiresConfirmation;
}
