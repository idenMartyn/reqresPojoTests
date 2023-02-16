package pojo;

public class RegisterUnsuccessfulResponse {
    private String error;

    public RegisterUnsuccessfulResponse(String error) {
        this.error = error;
    }

    public RegisterUnsuccessfulResponse() {}

    public String getError() {
        return error;
    }
}
