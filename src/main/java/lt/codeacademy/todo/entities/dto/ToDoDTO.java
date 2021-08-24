package lt.codeacademy.todo.entities.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.todo.entities.ToDo;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ToDoDTO {

    private Long id;

    private Long ownerId;

    private Long significanceId;

    private String toDoText;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime deadline;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    public ToDoDTO(ToDo toDo) {
        this.id = toDo.getId();
        this.ownerId = toDo.getOwner().getId();
        this.significanceId = toDo.getSignificance().getId();
        this.toDoText = toDo.getToDoText();
        this.deadline = toDo.getDeadline();
        this.created = toDo.getCreated();
        this.updated = toDo.getUpdated();
    }
}
