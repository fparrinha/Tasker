package tasker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasker.api.exceptions.InvalidRequestDataException;
import tasker.api.exceptions.UserAlreadyExistsException;
import tasker.api.exceptions.UserDoesNotExistException;
import tasker.api.exceptions.UserNotAuthorizedException;
import tasker.api.models.UserModel;
import tasker.api.requests.users.LoginRequest;
import tasker.api.requests.users.RegisterRequest;
import tasker.api.requests.users.UpdateRequest;
import tasker.api.responses.ApiResponse;
import tasker.api.responses.DataModelResponse;
import tasker.api.services.UsersService;
import tasker.api.utils.Shell;

@CrossOrigin
@RestController
@RequestMapping("users")
public class UsersController {

    /** Constants*/
    public static final String REGISTER_SUCCESS_MSG = "User was successfully registered";
    public static final String EMPTY_CREDENTIALS_MSG = "Some credentials are empty";
    public static final String LOGIN_ERROR_MSG = "The credentials provided do not match any user's in the Tasker platform.";
    public static final String LOGIN_SUCCESS_MSG = "Login successful";
    public static final String UPDATE_SUCCESS_MSG = "Update successful";

    @Autowired
    private UsersService usersService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            UserModel dataModel = new UserModel(request.username(), request.password(), request.email(),
                    request.firstName(), request.lastName());

            usersService.add(dataModel);
            return new ResponseEntity<>(new DataModelResponse(REGISTER_SUCCESS_MSG, dataModel), HttpStatus.OK);
        } catch (InvalidRequestDataException e) {
            String errorMessage = e.getMessage();
            Shell.getInstance().printError(errorMessage);
            return new ResponseEntity<>(new ApiResponse(EMPTY_CREDENTIALS_MSG), HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExistsException e) {
            String errorMessage = e.getMessage();
            Shell.getInstance().printError(errorMessage);
            return new ResponseEntity<>(new ApiResponse(errorMessage), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
           boolean authResult = usersService.authenticateUser(request.username(), request.password());

           // Validate auth result
           if (!authResult) {
               return new ResponseEntity<>(new ApiResponse(LOGIN_ERROR_MSG), HttpStatus.FORBIDDEN);
           }

           // User was successfully authenticated
           return new ResponseEntity<>(new ApiResponse(LOGIN_SUCCESS_MSG), HttpStatus.OK);
        } catch (InvalidRequestDataException e) {
            String errorMessage = e.getMessage();
            Shell.getInstance().printError(errorMessage);
            return new ResponseEntity<>(new ApiResponse(EMPTY_CREDENTIALS_MSG), HttpStatus.BAD_REQUEST);
        } catch (UserDoesNotExistException e) {
            String errorMessage = e.getMessage();
            Shell.getInstance().printError(errorMessage);
            return new ResponseEntity<>(new ApiResponse(errorMessage), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateRequest request) {
        try {
            UserModel dataModel = new UserModel(request.username(), request.password(), request.email(),
                    request.firstName(), request.lastName());
            usersService.update(dataModel);
            return new ResponseEntity<>(new DataModelResponse(UPDATE_SUCCESS_MSG, dataModel), HttpStatus.OK);
        } catch (UserNotAuthorizedException e) {
            String errorMessage = e.getMessage();
            Shell.getInstance().printError(errorMessage);
            return new ResponseEntity<>(new ApiResponse(errorMessage), HttpStatus.FORBIDDEN);
        } catch (InvalidRequestDataException e) {
            String errorMessage = e.getMessage();
            Shell.getInstance().printError(errorMessage);
            return new ResponseEntity<>(new ApiResponse(EMPTY_CREDENTIALS_MSG), HttpStatus.BAD_REQUEST);
        } catch (UserDoesNotExistException e) {
            String errorMessage = e.getMessage();
            Shell.getInstance().printError(errorMessage);
            return new ResponseEntity<>(new ApiResponse(errorMessage), HttpStatus.NOT_FOUND);
        }
    }


    /*
     * @TODO Add delete user and get user
     */
}
