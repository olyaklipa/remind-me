package olya.app.remindme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.Period;
import lombok.Data;

@Data
@Entity
@Table(name = "actions")
public class Action {
    @Id
    private Long id;
    @ManyToOne
    private Long subjectId;
    private String title;
    private LocalDate startDate;
    private Period frequency;
    private boolean isRecurring;
    private Period noticePeriodAdvance;
    private Period noticePeriodShort;
    private boolean requiresConfirmation;
}
