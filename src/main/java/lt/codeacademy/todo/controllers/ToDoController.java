package lt.codeacademy.todo.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lt.codeacademy.todo.entities.dto.requests.ToDoRequest;
import lt.codeacademy.todo.entities.dto.responses.ToDoResponse;
import lt.codeacademy.todo.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public ToDoResponse createToDo(@Valid @RequestBody ToDoRequest toDoRequest){
        return toDoService.createToDo(toDoRequest);
    }

    @ApiOperation(value = "Update to-do", tags = "updateToDo", httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated to-do record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ToDoResponse updateToDo(@PathVariable("id") Long id,@Valid @RequestBody ToDoRequest toDoRequest){
        return toDoService.updateToDo(toDoRequest, id);
    }

}
