package olya.app.remindme.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import lombok.Data;

@Data
@Entity
@Table(name = "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private String title;
    private LocalDate startDate;
    private long frequency; //in days
    private boolean isRecurring;
    private int noticePeriodAdvance; //in days
    private int noticePeriodShort; //in days
    private boolean requiresConfirmation;
}
