package it.nicolacorvo.TODO_list.todo.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTodoDto {
    @NotBlank(message = "Content is mandatory")
    private String content;
}
