package olya.app.remindme.repository;

import java.util.List;
import java.util.Optional;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    @Query("SELECT a FROM Action a " +
            "JOIN Subject s ON a.subject.id = s.id " +
            "WHERE a.id = :id AND s.user.id = :userId")
    Optional<Action> findActionByUserAndId(Long userId, Long id);

    @Query("SELECT a FROM Action a " +
            "JOIN Subject s ON a.subject.id = s.id " +
            "WHERE s.user.id = :userId")
    List<Action> findActionsByUser(Long userId);

//    @Query("SELECT a FROM Action a WHERE a.subject.id = :subjectId")
    List<Action> findBySubjectId(Long subjectId);

//    @Query("SELECT a FROM Action a WHERE a.subject.id = :subjectId AND a.title = :title")
    Optional<Action> findBySubjectIdAndTitle(Long subjectId, String title);

}
