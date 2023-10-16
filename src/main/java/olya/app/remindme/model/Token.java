package olya.app.remindme.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean revoked;
    private Timestamp validUntil;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
