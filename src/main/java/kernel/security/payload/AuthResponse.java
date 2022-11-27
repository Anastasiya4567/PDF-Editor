package kernel.security.payload;

public class AuthResponse {

    private final boolean success = true;
    private final String accessToken;
    private final String tokenType = "Bearer";

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public boolean isSuccess() {
        return success;
    }
}
