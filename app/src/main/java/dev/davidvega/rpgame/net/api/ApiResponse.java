package dev.davidvega.rpgame.net.api;

/**
 * The type Api response.
 */
public class ApiResponse {
    /**
     * The Status.
     */
    String status;

    /**
     * Instantiates a new Api response.
     *
     * @param status the status
     */
    public ApiResponse(String status) {
        this.status = status;
    }

    /**
     * Instantiates a new Api response.
     */
    public ApiResponse() {
    }


    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
