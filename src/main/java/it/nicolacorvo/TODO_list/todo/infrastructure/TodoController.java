package it.nicolacorvo.TODO_list.todo.infrastructure;

import it.nicolacorvo.TODO_list.todo.application.TodoService;
import it.nicolacorvo.TODO_list.todo.application.dto.CreateTodoDto;
import it.nicolacorvo.TODO_list.todo.application.dto.PatchTodoDto;
import it.nicolacorvo.TODO_list.todo.domain.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> findAll() {
        return todoService.findAllByEmail();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public Long createTodo(@RequestBody CreateTodoDto createTodoDto) {
        return todoService.createTodo(createTodoDto);
    }

    @PatchMapping("/{id}")
    public void patchTodo(@PathVariable Long id, @RequestBody PatchTodoDto patchTodoDto) {
        todoService.patchTodo(id, patchTodoDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Long id) {
        todoService.deleteTodoById(id);
    }


}