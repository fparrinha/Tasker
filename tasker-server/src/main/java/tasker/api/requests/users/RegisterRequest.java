package tasker.api.requests.users;

public record RegisterRequest(String username, String password, String email, String firstName, String lastName) {
}
