package olya.app.remindme.repository;

import olya.app.remindme.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjactRepository extends JpaRepository<Subject, Long> {
}
