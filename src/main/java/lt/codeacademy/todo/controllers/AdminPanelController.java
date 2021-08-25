package lt.codeacademy.todo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lt.codeacademy.todo.entities.dto.UserDTO;
import lt.codeacademy.todo.entities.dto.requests.UserUpdateRequest;
import lt.codeacademy.todo.entities.dto.responses.UserUpdateResponse;
import lt.codeacademy.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/admin/panel")
@Api(tags = "AdminPanelController")
public class AdminPanelController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get User", tags = "getUser", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get user"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUser(@PathVariable("id") Long id) {
        return new UserDTO(userService.getUser(id));
    }

    @ApiOperation(value = "Get all users", tags = "getUserList", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get list of to-dos"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/view")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getUsers().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @ApiOperation(value = "Delete user by id", tags = "deleteUser", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully delete user by id"),
            @ApiResponse(code = 404, message = "User not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/view/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Update user", tags = "updateUser", httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated tuser record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserUpdateResponse updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateRequest UserUpdateRequest){
        return userService.updateUser(UserUpdateRequest, id);
    }
}
