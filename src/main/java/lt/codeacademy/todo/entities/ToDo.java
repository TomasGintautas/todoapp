package lt.codeacademy.todo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lt.codeacademy.todo.entities.dto.requests.ToDoRequest;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "to_do")
@NoArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String toDoText;

    @CreationTimestamp
    @Column(name = "created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    @Column(name = "deadline")
    @JsonFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "significance_id")
    private Significance significance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public ToDo(ToDoRequest toDoRequest, Significance significance, User user) {
        this.toDoText = toDoRequest.getToDoText();
        this.deadline = toDoRequest.getDeadline();
        this.significance = significance;
        this.owner = user;
    }
}
