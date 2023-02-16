package pojo;

public class RegisterSuccessfulRequest {
    private String email;
    private String password;

    public RegisterSuccessfulRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterSuccessfulRequest() {}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
