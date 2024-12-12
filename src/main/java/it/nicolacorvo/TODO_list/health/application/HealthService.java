package it.nicolacorvo.TODO_list.health.application;

import it.nicolacorvo.TODO_list.health.domain.Health;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public Health getHealthStatus() {
        return new Health("UP");
    }
}