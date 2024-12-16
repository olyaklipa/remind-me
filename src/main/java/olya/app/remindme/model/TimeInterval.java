package olya.app.remindme.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "time_intervals")
public class TimeInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private TimeUnit timeUnit;
    private int quantity;

    public enum TimeUnit {
        DAYS,
        WEEKS,
        MONTHS,
        YEARS
    }
}
