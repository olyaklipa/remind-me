package olya.app.remindme.repository;

import java.util.List;
import java.util.Optional;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e " +
            "JOIN Action a ON e.action.id = a.id " +
            "JOIN Subject s ON a.subject.id = s.id " +
            "WHERE s.user.id = :userId AND e.id = :id")
    Optional<Event> findEventByUserAndId(Long userId, Long id);

//    @Query("SELECT e FROM Event e WHERE e.action.id = :actionId")
    List<Event> findByActionId(Long actionId);

    @Query("SELECT e FROM Event e " +
            "JOIN Action a ON e.action.id = a.id " +
            "JOIN Subject s ON a.subject.id = s.id " +
            "WHERE s.user.id = :userId")
    List<Event> findEventsByUser(Long userId);

//    @Query("SELECT e FROM Event e " +
//            "JOIN e.action a " +
//            "JOIN a.subject s " +
//            "WHERE s.user.id = :userId")
}
