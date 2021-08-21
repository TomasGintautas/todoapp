package lt.codeacademy.todo.entities.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.todo.entities.ToDo;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ToDoResponse {

    private Long id;

    private Long ownerId;

    private String toDoText;

    private String significance;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime deadline;

    public ToDoResponse(ToDo toDo) {
        this.id = toDo.getId();
        this.ownerId = toDo.getOwner().getId();
        this.toDoText = toDo.getToDoText();
        this.significance = toDo.getSignificance().getName();
        this.deadline = toDo.getDeadline() ;
    }
}
