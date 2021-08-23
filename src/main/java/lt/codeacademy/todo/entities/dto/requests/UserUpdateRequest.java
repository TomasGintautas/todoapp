package lt.codeacademy.todo.entities.dto.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequest {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Integer age;

    private String created;

    private String updated;
}
