package lt.codeacademy.todo.controllers;

import lt.codeacademy.todo.entities.dto.requests.ToDoRequest;
import lt.codeacademy.todo.entities.dto.responses.ToDoResponse;
import lt.codeacademy.todo.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/workspace")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @PostMapping
    public ToDoResponse createToDo(ToDoRequest toDoRequest){
        return toDoService.createToDo(toDoRequest);
    }


}
