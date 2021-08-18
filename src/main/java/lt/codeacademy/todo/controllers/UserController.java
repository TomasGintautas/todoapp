package lt.codeacademy.todo.controllers;

import lt.codeacademy.todo.entities.User;
import lt.codeacademy.todo.entities.dto.UserDTO;
import lt.codeacademy.todo.entities.dto.requests.RegisterRequest;
import lt.codeacademy.todo.entities.dto.responses.LoginResponse;
import lt.codeacademy.todo.security.JwtService;
import lt.codeacademy.todo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@AuthenticationPrincipal User user) {
        return new LoginResponse(jwtService.createToken(user));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new UserDTO(userService.createUser(new User(registerRequest)));
    }
}
