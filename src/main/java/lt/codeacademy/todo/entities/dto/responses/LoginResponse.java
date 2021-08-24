package lt.codeacademy.todo.entities.dto.responses;

import lombok.Data;
import lt.codeacademy.todo.entities.User;

@Data
public class LoginResponse {

    private final User user;
    private final String accessToken;
}