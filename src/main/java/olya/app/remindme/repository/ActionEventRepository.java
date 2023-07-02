package olya.app.remindme.repository;

import olya.app.remindme.model.ActionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionEventRepository extends JpaRepository<ActionEvent, Long> {
}
