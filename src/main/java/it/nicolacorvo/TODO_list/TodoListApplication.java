package it.nicolacorvo.TODO_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({it.nicolacorvo.TODO_list.health.infrastructure.HealthController.class, it.nicolacorvo.TODO_list.auth.infrastructure.AuthController.class,it.nicolacorvo.TODO_list.todo.infrastructure.TodoController.class})
public class TodoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}

}
