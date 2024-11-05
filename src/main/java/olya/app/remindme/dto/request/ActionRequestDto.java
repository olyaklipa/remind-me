package olya.app.remindme.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.TimeInterval;

@Getter
public class ActionRequestDto {
    @NotNull(message = "Subject is required")
    private Long subjectId;
    @NotBlank(message = "Title is required")
    private String title;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "Frequency is required")
    private TimeIntervalDto intervalBetweenEventsDto;
    @Min(value = 1, message = "Number of repeats must be a positive number")
    private Integer numOfRepeats;
    private Integer numOfDaysBeforeEventForAdvanceNotice;
    private Integer numOfDaysBeforeEventForShortNotice;
    @NotNull(message = "Notification method is required")
    private Action.NotificationMethod notificationMethod;
    private boolean requiresConfirmation;

    @Data
    public static class TimeIntervalDto {
        @Min(value = 1, message = "Quantity must be a positive number")
        private int quantity;
        @NotNull(message = "Time unit is required")
        private TimeInterval.TimeUnit timeUnit;
    }
}
