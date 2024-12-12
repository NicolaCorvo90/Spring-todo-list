package it.nicolacorvo.TODO_list.auth;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testAuthEndpoint() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "admin@admin.com");
        requestBody.put("password", "password");

        given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    public void testAuthEndpointBadRequest() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "wrong@admin.com");
        requestBody.put("password", "wrongpassword");

        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode());
    }

    @Test
    public void testAuthEndpointWithoutPassword() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "wrong@admin.com");

        Response response = given()
                .port(port)
                .contentType("application/json")
                .accept("application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .response();

        assertEquals(400, response.getStatusCode());
        assertEquals("Password is mandatory", response.jsonPath().getString("message"));
    }
}