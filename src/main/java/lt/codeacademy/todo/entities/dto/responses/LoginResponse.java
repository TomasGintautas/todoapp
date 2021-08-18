package lt.codeacademy.todo.entities.dto.responses;

import lombok.Data;

@Data
public class LoginResponse {

    private final String accessToken;
}