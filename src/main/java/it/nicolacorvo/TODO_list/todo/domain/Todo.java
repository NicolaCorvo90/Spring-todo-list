package it.nicolacorvo.TODO_list.todo.domain;

import it.nicolacorvo.TODO_list.todo.application.dto.PatchTodoDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import it.nicolacorvo.TODO_list.todo.application.dto.CreateTodoDto;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String content;
    private boolean completed;

    public static Todo createFromCreateTodoDto(String email, CreateTodoDto createTodoDto) {
        Todo todo = new Todo();
        todo.setEmail(email);
        todo.setContent(createTodoDto.getContent());
        todo.setCompleted(false);
        return todo;
    }

    public static Todo patchFromPatchTodoDto(Todo todo, PatchTodoDto patchTodoDto) {
        if(patchTodoDto.getContent() != null) {
            todo.setContent(patchTodoDto.getContent());
        }
        if(patchTodoDto.getCompleted() != null) {
            todo.setCompleted(patchTodoDto.getCompleted());
        }
        return todo;
    }
}
