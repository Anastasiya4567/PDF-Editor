package kernel.security.model;

public class UserBuilder {

    private String name;
    private String email;
    private String password;
    private String imageUrl;
    private AuthProvider provider;
    private String providerId;

    public UserBuilder setProvider(AuthProvider provider) {
        this.provider = provider;
        return this;
    }

    public UserBuilder setProviderId(String providerId) {
        this.providerId = providerId;
        return this;
    }


    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }


    public User createSocialUser() {
        return new User(provider, providerId, name, email, imageUrl);
    }

    public User createLocalUser() {
        return new User(name, email, password, provider);
    }

}
