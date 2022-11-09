package ir.surena.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.surena.userservice.controller.mapper.UserMapper;
import ir.surena.userservice.controller.model.request.CreateUserModel;
import ir.surena.userservice.controller.model.request.UpdatePasswordModel;
import ir.surena.userservice.controller.model.request.UpdateUserModel;
import ir.surena.userservice.controller.model.response.IdResponseModel;
import ir.surena.userservice.controller.model.response.UserResponseModel;
import ir.surena.userservice.entity.User;
import ir.surena.userservice.exception.ExceptionMessage;
import ir.surena.userservice.exception.UserException;
import ir.surena.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(UserController.USER_CONTROLLER_ADDRESS)
public class UserController {
    public static final String USER_CONTROLLER_ADDRESS = "/users";

    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create", description = "It creates a new user with given info.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "500", description = ExceptionMessage.USERNAME_IS_ALREADY_EXISTS_MSG)
    })
    public IdResponseModel create(@Parameter(description = "User information.")
                                  @RequestBody @Valid CreateUserModel createUserModel) throws UserException {
        User user = userMapper.createUserModelToUser(createUserModel);
        Long createdUserId = userService.create(user);
        return new IdResponseModel(createdUserId);
    }

    @DeleteMapping("/delete-by-username/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete", description = "It deletes a user by it's username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "500", description = ExceptionMessage.USER_NOT_FOUND_MSG)
    })
    public void deleteByUsername(@Parameter(description = "specify username.")
                                 @PathVariable("username") String username) throws UserException {
        userService.deleteByUsername(username);
    }

    @DeleteMapping("/delete-by-id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete", description = "It deletes a user by it's user id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "500", description = ExceptionMessage.USER_NOT_FOUND_MSG)
    })
    public void deleteByID(@Parameter(description = "specify user id.")
                           @PathVariable("id") Long id) throws UserException {
        userService.deleteById(id);
    }

    @PutMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update", description = "It updates user first name and last name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "500", description = ExceptionMessage.USER_NOT_FOUND_MSG)
    })
    public IdResponseModel update(@Parameter(description = "specify username.")
                                  @PathVariable("username") String username,
                                  @Parameter(description = "specify user update model; containing user first name and user last name.")
                                  @RequestBody @Valid UpdateUserModel updateUserModel) throws UserException {
        Long id = userService.updateByUsername(username, updateUserModel);
        return new IdResponseModel(id);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "GET", description = "It finds user information by it's username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "500", description = ExceptionMessage.USER_NOT_FOUND_MSG)
    })
    public UserResponseModel findByUsername(@Parameter(description = "specify username.")
                                            @PathVariable String username) throws UserException {
        User user = userService.findByUsername(username);
        UserResponseModel userResponseModel = userMapper.userToUserResponseModel(user);
        return userResponseModel;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "GET", description = "It fetches users information with pagination.")
    public List<UserResponseModel> findAll(@Parameter(description = "specify which page do you want to fetch.")
                                           @RequestParam("page") int page,
                                           @Parameter(description = "specify the size of each page.")
                                           @RequestParam("limit") int limit) {
        List<User> users = userService.findAll(page, limit);
        List<UserResponseModel> userResponseModels = userMapper.usersToUserResponseModels(users);
        return userResponseModels;
    }

    @PatchMapping("/{username}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Patch", description = "It changes user's password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated"),
            @ApiResponse(responseCode = "500", description = ExceptionMessage.USER_NOT_FOUND_MSG),
            @ApiResponse(responseCode = "500", description = ExceptionMessage.INCORRECT_PASSWORD_MSG)
    })
    public void updatePassword(
            @Parameter(description = "specify username.")
            @PathVariable("username") String username,
            @Parameter(description = "specify password information.")
            @RequestBody @Valid UpdatePasswordModel updatePasswordModel) throws UserException {
        userService.updatePassword(username, updatePasswordModel);
    }

}
