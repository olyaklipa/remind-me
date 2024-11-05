package olya.app.remindme.repository;

import olya.app.remindme.model.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeIntervalRepository extends JpaRepository<TimeInterval, Long> {
}
