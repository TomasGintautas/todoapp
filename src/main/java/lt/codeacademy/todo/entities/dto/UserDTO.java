package lt.codeacademy.todo.entities.dto;

import lombok.Data;
import lt.codeacademy.todo.entities.Role;
import lt.codeacademy.todo.entities.User;

import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Integer age;

    private Set<Role> roles;

    private String created;

    private String updated;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.roles = user.getRoles();
        this.created = user.getCreated().toString();
        this.updated = user.getUpdated().toString();
    }
}