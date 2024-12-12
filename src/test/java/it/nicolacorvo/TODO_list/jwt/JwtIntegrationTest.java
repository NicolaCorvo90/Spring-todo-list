package it.nicolacorvo.TODO_list.jwt;

import it.nicolacorvo.TODO_list.jwt.application.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class JwtIntegrationTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void testGenerateToken() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        assertThat(token).isNotNull();
        assertThat(token).contains(".");
    }

    @Test
    void testValidateToken() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        boolean isValid = jwtService.validateToken(token);

        assertThat(isValid).isTrue();
    }
}