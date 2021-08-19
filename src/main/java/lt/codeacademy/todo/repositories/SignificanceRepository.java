package lt.codeacademy.todo.repositories;

import lt.codeacademy.todo.entities.Significance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignificanceRepository extends JpaRepository<Significance, Long> {

    Significance findSignificanceByName(String name);
}
