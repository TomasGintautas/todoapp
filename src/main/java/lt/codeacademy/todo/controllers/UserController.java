package lt.codeacademy.todo.controllers;

import lt.codeacademy.todo.entities.User;
import lt.codeacademy.todo.entities.dto.UserDTO;
import lt.codeacademy.todo.entities.dto.requests.RegisterRequest;
import lt.codeacademy.todo.entities.dto.responses.LoginResponse;
import lt.codeacademy.todo.security.JwtService;
import lt.codeacademy.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@AuthenticationPrincipal User user) {
        return new LoginResponse(jwtService.createToken(user));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new UserDTO(userService.createUser(new User(registerRequest)));
    }

    @GetMapping("/user/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }
}
