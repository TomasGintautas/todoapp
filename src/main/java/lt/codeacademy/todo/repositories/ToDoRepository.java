package lt.codeacademy.todo.repositories;

import lt.codeacademy.todo.entities.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    Optional<List<ToDo>> getAllByOwner(Long ownerId);

    ToDo getById(Long id);
}
