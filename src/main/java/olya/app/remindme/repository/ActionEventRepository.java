package olya.app.remindme.repository;

import olya.app.remindme.model.ActionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionEventRepository extends JpaRepository<ActionEvent, Long> {
}
