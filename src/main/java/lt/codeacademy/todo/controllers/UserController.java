package lt.codeacademy.todo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @ApiOperation(value = "Login", tags = "login", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully logged in"),
    })
    @PostMapping("/login")
    public LoginResponse login(@AuthenticationPrincipal User user) {
        return new LoginResponse(user, jwtService.createToken(user));
    }

    @ApiOperation(value = "register", tags = "register", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully registered"),
            @ApiResponse(code = 400, message = "Validation failed")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new UserDTO(userService.createUser(new User(registerRequest)));
    }

    @ApiOperation(value = "Get User", tags = "getUser", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get user"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }
}
