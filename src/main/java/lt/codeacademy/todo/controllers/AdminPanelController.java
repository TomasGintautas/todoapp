package lt.codeacademy.todo.controllers;

import lt.codeacademy.todo.entities.dto.UserDTO;
import lt.codeacademy.todo.entities.dto.requests.ToDoRequest;
import lt.codeacademy.todo.entities.dto.requests.UserUpdateRequest;
import lt.codeacademy.todo.entities.dto.responses.ToDoResponse;
import lt.codeacademy.todo.entities.dto.responses.UserUpdateResponse;
import lt.codeacademy.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/admin/panel")
public class AdminPanelController {

    @Autowired
    private UserService userService;

    @GetMapping("/view/{id}")
//    @PreAuthorize("hasRole('ADMIN'))
    public UserDTO getUser(@PathVariable("id") Long id) {
        return new UserDTO(userService.getUser(id));
    }

    @GetMapping("/view")
    //    @PreAuthorize("hasRole('ADMIN'))
    public List<UserDTO> getAllUsers() {
        return userService.getUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //    @PreAuthorize("hasRole('ADMIN'))
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @PutMapping("/edit/{id}")
    //    @PreAuthorize("hasRole('ADMIN')")
    public UserUpdateResponse updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateRequest UserUpdateRequest){
        return userService.updateUser(UserUpdateRequest, id);
    }
}
