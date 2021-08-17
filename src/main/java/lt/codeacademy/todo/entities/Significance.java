package lt.codeacademy.todo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Significance {

    private Long id;

    private String name;

    private List<ToDo> todo;
}
