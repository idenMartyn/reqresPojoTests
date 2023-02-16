package pojo;

public class RegisterSuccessfulResponse {
    private Integer id;
    private String token;

    public RegisterSuccessfulResponse(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public RegisterSuccessfulResponse() {}

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
