package lt.codeacademy.todo.repositories;

import lt.codeacademy.todo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> getRoleByName(String name);
}
