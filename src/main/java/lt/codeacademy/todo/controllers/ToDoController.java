package lt.codeacademy.todo.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lt.codeacademy.todo.entities.dto.ToDoDTO;
import lt.codeacademy.todo.entities.dto.requests.ToDoRequest;
import lt.codeacademy.todo.entities.dto.responses.ToDoResponse;
import lt.codeacademy.todo.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/workspace")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @ApiOperation(value = "Create to-do", tags = "createToDo", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully update to-do record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping("/{id}/todo")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public ToDoResponse createToDo(@Valid @RequestBody ToDoRequest toDoRequest,@PathVariable("id") Long id){
        return toDoService.createToDo(toDoRequest);
    }

    @ApiOperation(value = "Update to-do", tags = "updateToDo", httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated to-do record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/todo/{toDoId}")
    public ToDoResponse updateToDo(@PathVariable("id") Long id,@Valid @RequestBody ToDoRequest toDoRequest, @PathVariable("toDoId") Long toDoId){
        return toDoService.updateToDo(toDoRequest, toDoId);
    }

    @ApiOperation(value = "Get all to-dos", tags = "getToDoList", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get list of to-dos"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}/todo")
    @PreAuthorize("hasRole('USER')")
    public List<ToDoDTO> getAllUserToDo(@PathVariable("id") Long id){
        return toDoService.getToDoList(id).stream().map(ToDoDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all to do today", tags = "getToDoToday", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get list of to-dos today"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}/today")
    @PreAuthorize("hasRole('USER')")
    public List<ToDoDTO> getToDoToday(@PathVariable("id") Long id){
        return toDoService.getToDoListByDateToday(id).stream().map(ToDoDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Get all to do by significance", tags = "getToDoBySignificance", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get list of to-dos by required significance"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}/{significance}")
    @PreAuthorize("hasRole('USER')")
    public List<ToDoDTO> getToDoBySignificance(@PathVariable("significance") String significance, @PathVariable("id") Long id){
        return toDoService.getToDoListBySignificance(id, significance).stream().map(ToDoDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Delete todo by id", tags = "deleteToDo", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully delete todo by id"),
            @ApiResponse(code = 404, message = "ToDo not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{id}/todo/{toDoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public void deleteToDo(@PathVariable("id") Long id, @PathVariable("toDoId") Long toDoId){
        toDoService.deleteToDo(toDoId);
    }

    @ApiOperation(value = "Delete todos past deadline", tags = "getToDo", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted old todos"),
            @ApiResponse(code = 404, message = "ToDos not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{id}/todo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public void deleteOldToDo(@PathVariable("id") Long id){
        toDoService.deleteOldToDo(id);
    }

    @ApiOperation(value = "Get todo", tags = "getToDo", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get one todo"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}/todo/{toDoId}")
    public ToDoDTO getToDO(@PathVariable("id") Long id, @PathVariable("toDoId") Long toDoId){
        return new ToDoDTO(toDoService.getToDo(toDoId));
    }
}
