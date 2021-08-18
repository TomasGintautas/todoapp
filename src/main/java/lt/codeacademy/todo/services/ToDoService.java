package lt.codeacademy.todo.services;

import lt.codeacademy.todo.entities.ToDo;
import lt.codeacademy.todo.exceptions.FieldNotFoundException;
import lt.codeacademy.todo.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Transactional
    public List<ToDo> getToDoList(Long userId){
        return toDoRepository.getAllByOwner(userId).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", userId.toString()));
    }

    @Transactional
    public List<ToDo> getToDoListBySignificance(Long userId, Long significanceId){
        return toDoRepository
                .getAllByOwner(userId).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", userId.toString()))
                .stream()
                .filter(significance -> significance.getSignificance().getId().equals(significanceId))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ToDo> getToDoListByDateToday(Long userId){
        LocalDate dateToday = LocalDate.now();
        return toDoRepository
                .getAllByOwner(userId).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", userId.toString()))
                .stream()
                .filter(toDoToday -> toDoToday.getDeadline().toString().substring(0,10).replace(" ","-").equals(dateToday.toString()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ToDo createToDo(ToDo todo){
        return toDoRepository.save(todo);
    }

    @Transactional
    public ToDo updateToDo(ToDo updateToDo) {
        ToDo toDo = toDoRepository.getById(updateToDo.getId());
        updateToDo.setCreated(toDo.getCreated());
        return toDoRepository.save(updateToDo);
    }

    @Transactional
    public void deleteToDo(Long id){
        toDoRepository.deleteById(id);
    }

    @Transactional
    public void deleteOldToDo(Long id){
        toDoRepository
                .getAllByOwner(id).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", id.toString()))
                .stream()
                .filter(removable -> removable.getDeadline().isBefore(LocalDateTime.now()))
                .forEach(removable -> toDoRepository.deleteById(removable.getId()));
    }

}
