package olya.app.remindme.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import olya.app.remindme.model.Role;
import olya.app.remindme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);

//    @Query("SELECT u FROM User u WHERE u.email = :email AND u.active = true")
    Optional<User> findByEmailAndActiveTrue(String email);

//    @Query("SELECT u FROM User u WHERE u.id = :id AND u.active = true")
    Optional<User> findByIdAndActiveTrue(Long id);

//    @Query("SELECT u.roles FROM User u WHERE u.id = :userId")
//    Set<Role> findRolesById(Long userId);
}
