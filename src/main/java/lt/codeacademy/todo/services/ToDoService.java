package lt.codeacademy.todo.services;

import lt.codeacademy.todo.entities.ToDo;
import lt.codeacademy.todo.exceptions.FieldNotFoundException;
import lt.codeacademy.todo.repositories.SignificanceRepository;
import lt.codeacademy.todo.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    private final UserService userService;

    private final ToDoRepository toDoRepository;

    private final SignificanceRepository significanceRepository;

    @Autowired
    public ToDoService(UserService userService, ToDoRepository toDoRepository, SignificanceRepository significanceRepository) {
        this.userService = userService;
        this.toDoRepository = toDoRepository;
        this.significanceRepository = significanceRepository;
    }

    public List<ToDo> getToDoList(Long user_id){
        return toDoRepository.getAllByOwner(user_id).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", user_id.toString()));
    }

    public List<ToDo> getToDoListBySignificance(Long user_id, Long significance_id){
        return toDoRepository
                .getAllByOwner(user_id).orElseThrow(() -> new FieldNotFoundException("User not found by given ID: ", user_id.toString()))
                .stream()
                .filter(significance -> significance.getSignificance().getId().equals(significance_id))
                .collect(Collectors.toList());
    }

}
