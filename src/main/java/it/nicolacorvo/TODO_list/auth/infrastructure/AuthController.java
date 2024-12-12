package it.nicolacorvo.TODO_list.auth.infrastructure;

import it.nicolacorvo.TODO_list.auth.application.AuthService;
import it.nicolacorvo.TODO_list.auth.application.dto.LoginRequest;
import it.nicolacorvo.TODO_list.auth.application.readmodel.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "login", produces = "application/json")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}