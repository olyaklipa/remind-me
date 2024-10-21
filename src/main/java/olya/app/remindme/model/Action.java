package olya.app.remindme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
 /*
 - title must be unique per user AND subject
 - active is true unless suspended by user, can go true again when reactivated
 - start date -the first occurrence (initial event) from which frequency starts counting or the first occurrence after reactivation set by the user
 - repeatEveryNumOfDays - in days, required
 - numOfRepeats - if not specified the app will keep reminding unless suspend or delete the action
 - numOfDaysBeforeEventFor.. (advance and short notice are optional. the reminder will take place on the actual day in the morning anyway)
 - requiresConfirmation if true - then in the evening of the actual day the confirmation will be asked (options: DONE, SKIP, PENDING, NA)
 if no confirmation received or requiresConfirmation is false -> the status of the event will be NA.
 for DONE -> the status of the event will be DONE, the start date and repeatEveryNumOfDays not affected
 for SKIP -> the status for the event will be SKIPPED, the start date and repeatEveryNumOfDays not affected
 for PENDING -> the status for the event will be NA and confirmation will be asking every day in the evening until one of the below options will be chosen.
 The options will be:
 DONE_AND_KEEP_START_DATE (start date: ...),
 DONE_AND_UPDATE_START_DATE (current date),
 SKIP_AND_KEEP_START_DATE (start date: ...),
 SKIP_AND_UPDATE_START_DATE (current date),
 PENDING - to keep asking next day and so...

*/
@Data
@Entity
@Table(name = "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private String title;
    private boolean active;
    private LocalDate startDate; //required
    private LocalDate lastExecutionDate; //initially null, set automatically by ReminderJob
    private int repeatEveryNumOfDays; //required
    private Integer numOfRepeats;
    private int repeatsCount; // initially 0, set automatically by ReminderJob
    private Integer numOfDaysBeforeEventForAdvanceNotice; //not required
    private Integer numOfDaysBeforeEventForShortNotice; //not required
    @Enumerated(value = EnumType.STRING)
    private NotificationMethod notificationMethod;
    private boolean requiresConfirmation;


    public enum NotificationMethod {
        EMAIL,
        TELEGRAM,
        EMAIL_AND_TELEGRAM
    }
}
