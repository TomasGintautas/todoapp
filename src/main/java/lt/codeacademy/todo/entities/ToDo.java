package lt.codeacademy.todo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {

    private Long id;

    private String toDoText;

    private LocalDateTime dateCreated;

    private LocalDateTime deadline;

    private Significance significance;
}
