package lt.codeacademy.todo.services;

import lt.codeacademy.todo.entities.ToDo;
import lt.codeacademy.todo.entities.dto.requests.ToDoRequest;
import lt.codeacademy.todo.entities.dto.responses.ToDoResponse;
import lt.codeacademy.todo.exceptions.FieldNotFoundException;
import lt.codeacademy.todo.repositories.SignificanceRepository;
import lt.codeacademy.todo.repositories.ToDoRepository;
import lt.codeacademy.todo.repositories.UserRepository;
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
    private final SignificanceRepository significanceRepository;
    private final UserRepository userRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, SignificanceRepository significanceRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.significanceRepository = significanceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<ToDoResponse> getToDoList(Long userId) {
        return toDoRepository.getAllByOwner(userId).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", userId.toString()))
                .stream()
                .map(todo -> new ToDoResponse(todo))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ToDo> getToDoListBySignificance(Long userId, Long significanceId) {
        return toDoRepository
                .getAllByOwner(userId).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", userId.toString()))
                .stream()
                .filter(significance -> significance.getSignificance().getId().equals(significanceId))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ToDoResponse> getToDoListByDateToday(Long userId) {
        LocalDate dateToday = LocalDate.now();
        return toDoRepository
                .getAllByOwner(userId).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", userId.toString()))
                .stream()
                .filter(toDoToday -> toDoToday.getDeadline().toString().substring(0, 10).replace(" ", "-").equals(dateToday.toString()))
                .map(ToDoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ToDoResponse createToDo(ToDoRequest toDoRequest) {
        return new ToDoResponse(toDoRepository.save(new ToDo(
                toDoRequest,
                significanceRepository.findSignificanceByName(toDoRequest.getSignificance()),
                userRepository.getById(toDoRequest.getOwnerId()))));
    }

    @Transactional
    public ToDoResponse updateToDo(ToDoRequest updateToDoRequest, Long id) {
        ToDo toDo = toDoRepository.getById(id);
        toDo.setSignificance(significanceRepository.findSignificanceByName(updateToDoRequest.getSignificance()));
        toDo.setToDoText(updateToDoRequest.getToDoText());
        toDo.setDeadline(updateToDoRequest.getDeadline());
        toDo.setUpdated(LocalDateTime.now());

        return new ToDoResponse(toDoRepository.save(new ToDo(
                updateToDoRequest,
                significanceRepository.findSignificanceByName( updateToDoRequest.getSignificance()),
                userRepository.getById(updateToDoRequest.getOwnerId()))));
    }

    @Transactional
    public void deleteToDo(Long id) {
        toDoRepository.deleteById(id);}

    @Transactional
    public void deleteOldToDo(Long id) {
        toDoRepository
                .getAllByOwner(id).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", id.toString()))
                .stream()
                .filter(removable -> removable.getDeadline().isBefore(LocalDateTime.now()))
                .forEach(removable -> toDoRepository.deleteById(removable.getId()));
    }

}
