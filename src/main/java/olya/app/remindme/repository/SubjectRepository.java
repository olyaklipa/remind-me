package olya.app.remindme.repository;

import java.util.List;
import java.util.Optional;
import olya.app.remindme.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
//    @Query("SELECT s FROM Subject s WHERE s.user.id = :userId AND s.id = :id")
    Optional<Subject> findByUserIdAndId(Long userId, Long id);

//    @Query("SELECT s FROM Subject s WHERE s.user.id = :userId AND s.name = :name")
    Optional<Subject> findByUserIdAndName(Long userId, String name);

//    @Query("SELECT s FROM Subject s WHERE s.user.id = :userId")
    List<Subject> findByUserId(Long userId);


}
