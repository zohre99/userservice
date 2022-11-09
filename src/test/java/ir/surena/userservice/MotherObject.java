package ir.surena.userservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import ir.surena.userservice.controller.model.request.CreateUserModel;
import ir.surena.userservice.controller.model.request.UpdatePasswordModel;
import ir.surena.userservice.controller.model.request.UpdateUserModel;
import ir.surena.userservice.controller.model.response.UserResponseModel;
import ir.surena.userservice.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MotherObject {

    public static final String ANY_STRING = "Zohre";
    public static final Long ANY_ID = 1L;
    public static final String ANY_FIRST_NAME = "Zohre";
    public static final String ANY_LAST_NAME = "Khedmati";
    public static final String ANY_USERNAME = "z.khedmati";
    public static final String ANY_VALID_PASSWORD = "12345678";
    public static final String ANY_PASSWORD = "z212oh2es23";
    public static final String ANY_INVALID_PASSWORD = "123";
    public static final int ANY_PAGE = 1;
    public static final int ANY_LIMIT = 1;

    public static CreateUserModel anyValidCreateUserModel() {
        CreateUserModel createUserModel = new CreateUserModel();

        createUserModel.setFirstName(ANY_FIRST_NAME);
        createUserModel.setLastName(ANY_LAST_NAME);
        createUserModel.setUsername(ANY_USERNAME);
        createUserModel.setPassword(ANY_VALID_PASSWORD);

        return createUserModel;
    }

    public static CreateUserModel anyInvalidCreateUserModel() {
        CreateUserModel createUserModel = new CreateUserModel();

        createUserModel.setLastName(ANY_LAST_NAME);
        createUserModel.setPassword(ANY_VALID_PASSWORD);

        return createUserModel;
    }

    public static CreateUserModel invalidPasswordCreateUserModel() {
        CreateUserModel createUserModel = new CreateUserModel();

        createUserModel.setFirstName(ANY_FIRST_NAME);
        createUserModel.setLastName(ANY_LAST_NAME);
        createUserModel.setUsername(ANY_USERNAME);
        createUserModel.setPassword(ANY_INVALID_PASSWORD);

        return createUserModel;
    }

    public static User anyValidUser() {
        User user = new User();
        Date createdDate = new Date();

        user.setId(ANY_ID);
        user.setFirstName(ANY_FIRST_NAME);
        user.setLastName(ANY_LAST_NAME);
        user.setUsername(ANY_USERNAME);
        user.setPassword(ANY_VALID_PASSWORD);
        user.setCreatedDate(createdDate);
        user.setModifiedDate(createdDate);

        return user;
    }

    public static List<User> anyValidUserList() {
        User user = new User();
        Date createdDate = new Date();

        user.setId(ANY_ID);
        user.setFirstName(ANY_FIRST_NAME);
        user.setLastName(ANY_LAST_NAME);
        user.setUsername(ANY_USERNAME);
        user.setPassword(ANY_VALID_PASSWORD);
        user.setCreatedDate(createdDate);
        user.setModifiedDate(createdDate);

        return Arrays.asList(user);
    }

    public static UserResponseModel anyValidUserResponseModel() {
        UserResponseModel userResponseModel = new UserResponseModel();
        Date createdDate = new Date();

        userResponseModel.setId(ANY_ID);
        userResponseModel.setFirstName(ANY_FIRST_NAME);
        userResponseModel.setLastName(ANY_LAST_NAME);
        userResponseModel.setUsername(ANY_USERNAME);
        userResponseModel.setCreatedDate(createdDate);
        userResponseModel.setModifiedDate(createdDate);

        return userResponseModel;
    }

    public static List<UserResponseModel> anyValidUserResponseModelList() {
        UserResponseModel userResponseModel = new UserResponseModel();
        Date createdDate = new Date();

        userResponseModel.setId(ANY_ID);
        userResponseModel.setFirstName(ANY_FIRST_NAME);
        userResponseModel.setLastName(ANY_LAST_NAME);
        userResponseModel.setUsername(ANY_USERNAME);
        userResponseModel.setCreatedDate(createdDate);
        userResponseModel.setModifiedDate(createdDate);

        return Arrays.asList(userResponseModel);
    }

    public static UpdateUserModel anyUpdateUserModel() {
        UpdateUserModel updateUserModel = new UpdateUserModel();

        updateUserModel.setFirstName(ANY_STRING);
        updateUserModel.setFirstName(ANY_LAST_NAME);

        return updateUserModel;
    }

    public static UpdatePasswordModel anyValidUpdatePasswordModel() {
        UpdatePasswordModel updateUserModel = new UpdatePasswordModel();
        updateUserModel.setCurrentPassword(ANY_VALID_PASSWORD);
        updateUserModel.setNewPassword(ANY_PASSWORD);
        return updateUserModel;
    }

    public static UpdatePasswordModel anyInvalidUpdatePasswordModel() {
        UpdatePasswordModel updateUserModel = new UpdatePasswordModel();
        updateUserModel.setCurrentPassword(ANY_VALID_PASSWORD);
        updateUserModel.setNewPassword(ANY_INVALID_PASSWORD);
        return updateUserModel;
    }

    public static String toJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
