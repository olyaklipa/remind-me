package olya.app.remindme.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import olya.app.remindme.model.Action;

@Getter
public class ActionRequestDto {
    @NotNull(message = "Subject is required")
    private Long subjectId;
    @NotBlank(message = "Title is required")
    private String title;
    @NotNull(message = "Status is required")
    private boolean active;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "Frequency is required")
    private int repeatEveryNumOfDays;
    private Integer numOfRepeats;
    private Integer numOfDaysBeforeEventForAdvanceNotice;
    private Integer numOfDaysBeforeEventForShortNotice;
    @NotNull(message = "Notification method is required")
    private Action.NotificationMethod notificationMethod;
    private boolean requiresConfirmation;
}
