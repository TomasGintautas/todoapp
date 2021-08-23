package lt.codeacademy.todo.entities.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.todo.entities.User;

@Data
@NoArgsConstructor
public class UserUpdateResponse {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Integer age;

    private String created;

    private String updated;

    public UserUpdateResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.created = user.getCreated().toString();
        this.updated = user.getUpdated().toString();
    }
}
