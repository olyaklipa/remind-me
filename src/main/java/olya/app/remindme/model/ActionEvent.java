package olya.app.remindme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class ActionEvent {
    @Id
    private Long id;
    @ManyToOne
    private Long actionId;
    private LocalDate date;
    private String note;
    private boolean isDone; //only if requiresConfirmation is true
}
