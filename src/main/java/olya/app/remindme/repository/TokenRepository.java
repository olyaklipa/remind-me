package olya.app.remindme.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import olya.app.remindme.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

//    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.revoked = false")
    List<Token> findByUserIdAndRevokedFalse(Long userId);

    Optional<Token> findByToken(String token);

//    @Query("SELECT t FROM Token t WHERE t.validUntil < CURRENT_TIMESTAMP")
    List<Token> findByValidUntilBefore(Timestamp currentTime);
}
