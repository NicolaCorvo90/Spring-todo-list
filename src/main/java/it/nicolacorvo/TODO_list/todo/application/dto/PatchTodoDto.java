package it.nicolacorvo.TODO_list.todo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchTodoDto {
    private String content;

    private Boolean completed;
}
