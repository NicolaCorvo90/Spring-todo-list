package it.nicolacorvo.TODO_list.auth.application;

import it.nicolacorvo.TODO_list.auth.application.dto.LoginRequest;
import it.nicolacorvo.TODO_list.auth.application.readmodel.LoginResponse;
import it.nicolacorvo.TODO_list.jwt.application.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Value("${LOGIN_EMAIL}")
    private String loginEmail;

    @Value("${LOGIN_PASSWORD}")
    private String loginPassword;

    @Autowired
    private JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        if(loginRequest.getEmail().equals(loginEmail) && loginRequest.getPassword().equals(loginPassword)) {
            return new LoginResponse(jwtService.generateToken(loginRequest.getEmail()));
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
    }
}
