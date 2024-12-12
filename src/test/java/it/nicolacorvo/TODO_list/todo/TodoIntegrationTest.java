package it.nicolacorvo.TODO_list.todo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import it.nicolacorvo.TODO_list.todo.application.dto.CreateTodoDto;
import it.nicolacorvo.TODO_list.todo.application.dto.PatchTodoDto;
import it.nicolacorvo.TODO_list.todo.domain.Todo;
import it.nicolacorvo.TODO_list.todo.domain.TodoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.proxy;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    public String getToken() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "admin@admin.com");
        requestBody.put("password", "password");

        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")
                .then().extract().response();

        return response.jsonPath().getString("token");
    }

    @Test
    public void testFindAllTodosWithoutAuth() {
        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get("/todos")
                .then()
                .extract()
                .response();

        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testFindAllTodos() {
        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get("/todos")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        List<Todo> results = response.jsonPath().getList("$");
        assertEquals(3, results.size());
    }

    @Test
    public void testFindTodoByIdWithoutAuth() {
        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get("/todos/1")
                .then()
                .extract()
                .response();

        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testFindTodoById() {
        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .header("Authorization", "Bearer " + getToken())
                .when()
                .get("/todos/1")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());
        Todo result = response.jsonPath().getObject("$", Todo.class);
        assertEquals(1, result.getId());
        assertEquals("admin@admin.com", result.getEmail());
        assertEquals("Content 1", result.getContent());
        assertFalse(result.isCompleted());
    }

    @Test
    public void testCreateTodoWithoutAuth() {
        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .post("/todos")
                .then()
                .extract()
                .response();

        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testCreateTodo() {
        CreateTodoDto createTodoDto = new CreateTodoDto("Content test");

        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .header("Authorization", "Bearer " + getToken())
                .body(createTodoDto)
                .when()
                .post("/todos")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());
        Long id = ((Integer)response.jsonPath().get()).longValue();

        Todo result = todoRepository.findById(id).orElseThrow();
        assertEquals(id, result.getId());
        assertEquals("admin@admin.com", result.getEmail());
        assertEquals("Content test", result.getContent());
        assertFalse(result.isCompleted());

        todoRepository.deleteById(id);
    }

    @Test
    public void testPatchTodoWithoutAuth() {
        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .patch("/todos/1")
                .then()
                .extract()
                .response();

        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testPatchTodo() {
        Long id =Long.valueOf("1");
        Todo oldTodo = todoRepository.findById(id).orElseThrow();
        PatchTodoDto patchTodoDto = new PatchTodoDto("Updated content", true);

        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .header("Authorization", "Bearer " + getToken())
                .body(patchTodoDto)
                .when()
                .patch("/todos/1")
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());
        Todo result = todoRepository.findById(id).orElseThrow();
        assertEquals(id, result.getId());
        assertEquals("admin@admin.com", result.getEmail());
        assertEquals("Updated content", result.getContent());
        assertTrue(result.isCompleted());

        todoRepository.save(oldTodo);
    }

    @Test
    public void testDeleteTodoWithoutAuth() {
        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .delete("/todos/1")
                .then()
                .extract()
                .response();

        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void testDeleteTodo() {
        Todo todo = new Todo();
        todo.setEmail("test@test.it");
        todo.setContent("Test content");
        todo.setCompleted(true);

        Todo savedTodo = todoRepository.save(todo);

        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .header("Authorization", "Bearer " + getToken())
                .when()
                .delete("/todos/" + savedTodo.getId())
                .then()
                .extract()
                .response();

        assertEquals(200, response.getStatusCode());

        todoRepository.findById(savedTodo.getId()).ifPresent(t -> fail("Todo not deleted"));
    }
}