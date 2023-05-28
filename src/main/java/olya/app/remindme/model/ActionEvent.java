package olya.app.remindme.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class ActionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;
    private LocalDate date;
    private String note;
    private boolean isDone; //only if requiresConfirmation is true
}
