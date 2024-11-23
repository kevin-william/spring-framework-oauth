package autonomous.repositories;

import autonomous.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
}
