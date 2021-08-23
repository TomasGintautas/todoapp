package lt.codeacademy.todo.entities.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ToDoRequest {

    private Long ownerId;

    @ApiModelProperty(notes = "To Do Text", required = true, name = "toDoText", value = "Buy milk", example = "Buy milk")
    private String toDoText;

    @ApiModelProperty(notes = "The level of significance", required = true, name = "significance", value = "Ordinary", example = "Ordinary")
    private String significance;

    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    @ApiModelProperty(notes = "Deadline of the note", required = true, name = "deadline", value = "2021-08-25-20:00", example = "2021-08-25-20:00")
    private LocalDateTime deadline;

}
