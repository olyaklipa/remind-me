package olya.app.remindme.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;
    private LocalDate date;
    @Enumerated(value = EnumType.STRING)
    private Status status;


    public enum Status {
        DONE,
        SKIPPED,
        PENDING,
        NA
    }
}
