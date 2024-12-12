package it.nicolacorvo.TODO_list.health.domain;

public class Health {
    private String status;

    public Health(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}