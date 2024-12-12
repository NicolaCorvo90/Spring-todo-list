package it.nicolacorvo.TODO_list.health.infrastructure;

import it.nicolacorvo.TODO_list.health.domain.Health;
import it.nicolacorvo.TODO_list.health.application.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    @GetMapping
    public Health getHealth() {
        return healthService.getHealthStatus();
    }
}