package lt.codeacademy.todo.entities.dto.requests;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

    @Data
    public class RegisterRequest {

        @NotBlank
        private String username;

        @Size(min = 6, max = 30)
        private String password;

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        @NotNull
        @Range(min = 1, max = 140)
        private Integer age;

        public RegisterRequest(String username, String password, String firstName, String lastName, Integer age) {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }
}
