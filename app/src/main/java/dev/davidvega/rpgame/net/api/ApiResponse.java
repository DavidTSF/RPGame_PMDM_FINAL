package dev.davidvega.rpgame.net.api;

public class ApiResponse {
    String status;

    public ApiResponse(String status) {
        this.status = status;
    }

    public ApiResponse() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
