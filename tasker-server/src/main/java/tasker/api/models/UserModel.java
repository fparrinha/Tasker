package tasker.api.models;

import static tasker.api.utils.Utils.isStringNull;

public record UserModel(String username, String password, String email, String firstName, String lastName) implements DataModel {

    @Override
    public boolean isDataCorrupt() {
        return isStringNull(email) || isStringNull(username) || isStringNull(password) ||
                isStringNull(firstName) || isStringNull(lastName);
    }
}
