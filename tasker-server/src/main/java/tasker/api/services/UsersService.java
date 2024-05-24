package tasker.api.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasker.api.exceptions.InvalidRequestDataException;
import tasker.api.exceptions.UserAlreadyExistsException;
import tasker.api.exceptions.UserDoesNotExistException;
import tasker.api.exceptions.UserNotAuthorizedException;
import tasker.api.models.UserModel;
import tasker.api.repositories.UsersRepository;
import tasker.api.resources.User;

import java.util.Optional;

import static tasker.api.utils.Utils.isStringNull;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public User add(UserModel model) throws InvalidRequestDataException, UserAlreadyExistsException {
        if (model.isDataCorrupt()) {
            throw new InvalidRequestDataException("Create User");
        }

        // Check user existence
        Optional<User> query = usersRepository.findById(model.username());
        if (query.isPresent()) {
            throw new UserAlreadyExistsException(model.username());
        }

        // Create new user
        User user = new User();
        user.setEmail(model.email());
        user.setUsername(model.username());
        user.setPassword(model.password());
        user.setFirstName(model.firstName());
        user.setLastName(model.lastName());
        return usersRepository.save(user);
    }

    /**
     * Updates the user's info except its password and username, since these require more security
     * <p>
     *     Username and password are still required to authenticate the user before updating its values
     * </p>
     * @param model the user's data model
     * @return updated user
     * @throws InvalidRequestDataException data is corrupt
     * @throws UserDoesNotExistException user already exists in the system
     * @throws UserNotAuthorizedException user does not have the authorization required to update
     */
    @Transactional
    public User update(UserModel model) throws InvalidRequestDataException, UserDoesNotExistException, UserNotAuthorizedException {
        if (model.isDataCorrupt()) {
            throw new InvalidRequestDataException("Update User");
        }

        // Check user existence
        Optional<User> query = usersRepository.findById(model.username());
        if (query.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        // Authenticate user
        if (!authenticateUser(model.username(), model.password())) {
            throw new UserNotAuthorizedException("Wrong password");
        }

        // Update user
        User user = query.get();
        user.setEmail(model.email());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        usersRepository.save(user);
        return user;
    }

    @Transactional
    public User delete(String username, String password) throws InvalidRequestDataException, UserDoesNotExistException, UserNotAuthorizedException {
        if (isStringNull(username) || isStringNull(password)) {
            throw new InvalidRequestDataException("User Authentication");
        }

        // Check user existence
        Optional<User> query = usersRepository.findById(username);
        if (query.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        // Authenticate user
        if (!authenticateUser(username, password)) {
            throw new UserNotAuthorizedException("Wrong password");
        }

        // Delete user
        User user = query.get();
        usersRepository.delete(user);
        return user;
    }


    /**
     * Validates the user password
     * @param username username of the user to find
     * @param password its password to validate
     * @return password is correct or not for the given user
     */
    public boolean authenticateUser(String username, String password) throws InvalidRequestDataException, UserDoesNotExistException {
        if (isStringNull(username) || isStringNull(password)) {
            throw new InvalidRequestDataException("User Authentication");
        }

        // Check user existence
        Optional<User> query = usersRepository.findById(username);
        if (query.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        User user = query.get();
        return user.getPassword().equals(password);
    }

    /**
     * Checks for the username's existence
     * @param username user's username to find
     * @return does user exist or not
     */
    public boolean userExists(String username) throws InvalidRequestDataException {
        if (isStringNull(username)) {
            throw new InvalidRequestDataException("User exists");
        }

        // Check user existence
        Optional<User> query = usersRepository.findById(username);
        return query.isPresent();
    }
}
