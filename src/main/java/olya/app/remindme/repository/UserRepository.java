package olya.app.remindme.repository;

import java.util.Optional;
import java.util.Set;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.roles FROM User u WHERE u.id = :userId")
    Set<Role> findRolesById(Long userId);
}
