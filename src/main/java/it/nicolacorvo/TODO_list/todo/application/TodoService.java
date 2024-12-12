package it.nicolacorvo.TODO_list.todo.application;

import it.nicolacorvo.TODO_list.todo.application.dto.CreateTodoDto;
import it.nicolacorvo.TODO_list.todo.application.dto.PatchTodoDto;
import it.nicolacorvo.TODO_list.todo.domain.Todo;
import it.nicolacorvo.TODO_list.todo.domain.TodoRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> findAllByEmail() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return todoRepository.findByEmail(email);
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    public Long createTodo(CreateTodoDto createTodoDto) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Todo saved = todoRepository.save(Todo.createFromCreateTodoDto(email, createTodoDto));
        return saved.getId();
    }

    public void patchTodo(Long id, PatchTodoDto patchTodoDto) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if(todo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        todoRepository.save(Todo.patchFromPatchTodoDto(todo, patchTodoDto));
    }

    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }
}