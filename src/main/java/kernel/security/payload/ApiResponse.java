package kernel.security.payload;

public class ApiResponse {

    private final boolean success;
    private final String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
