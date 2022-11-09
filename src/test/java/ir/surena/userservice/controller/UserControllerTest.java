package ir.surena.userservice.controller;

import ir.surena.userservice.controller.mapper.UserMapper;
import ir.surena.userservice.controller.model.request.CreateUserModel;
import ir.surena.userservice.controller.model.request.UpdatePasswordModel;
import ir.surena.userservice.controller.model.request.UpdateUserModel;
import ir.surena.userservice.controller.model.response.UserResponseModel;
import ir.surena.userservice.entity.User;
import ir.surena.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static ir.surena.userservice.MotherObject.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    UserMapper userMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void given_valid_create_user_model_then_return_2xx() throws Exception {
        CreateUserModel createUserModel = anyValidCreateUserModel();
        User user = anyValidUser();

        when(userMapper.createUserModelToUser(createUserModel)).thenReturn(user);
        when(userService.create(user)).thenReturn(ANY_ID);

        mockMvc.perform(post(UserController.USER_CONTROLLER_ADDRESS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(createUserModel)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(ANY_ID));
    }

    @Test
    void given_invalid_password_in_create_user_model_then_return_4xx() throws Exception {
        CreateUserModel createUserModel = invalidPasswordCreateUserModel();

        mockMvc.perform(post(UserController.USER_CONTROLLER_ADDRESS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(createUserModel)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void given_invalid_create_user_model_then_return_4xx() throws Exception {
        CreateUserModel createUserModel = anyInvalidCreateUserModel();

        mockMvc.perform(post(UserController.USER_CONTROLLER_ADDRESS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(createUserModel)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void given_valid_username_then_return_2xx() throws Exception {
        doNothing().when(userService).deleteByUsername(ANY_USERNAME);

        mockMvc.perform(delete(UserController.USER_CONTROLLER_ADDRESS + "/delete-by-username/" + ANY_USERNAME))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void given_valid_id_then_return_2xx() throws Exception {
        doNothing().when(userService).deleteById(ANY_ID);

        mockMvc.perform(delete(UserController.USER_CONTROLLER_ADDRESS + "/delete-by-id/" + ANY_ID))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void given_any_update_user_model_then_return_2xx() throws Exception {
        UpdateUserModel updateUserModel = anyUpdateUserModel();
        when(userService.updateByUsername(ANY_USERNAME, updateUserModel)).thenReturn(ANY_ID);

        mockMvc.perform(put(UserController.USER_CONTROLLER_ADDRESS + "/" + ANY_USERNAME)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(updateUserModel)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(ANY_ID));
    }

    @Test
    void given_valid_username_then_return_user_info_and_2xx() throws Exception {
        User user = anyValidUser();
        UserResponseModel userResponseModel = anyValidUserResponseModel();

        when(userService.findByUsername(ANY_USERNAME)).thenReturn(user);
        when(userMapper.userToUserResponseModel(user)).thenReturn(userResponseModel);

        mockMvc.perform(get(UserController.USER_CONTROLLER_ADDRESS + "/" + ANY_USERNAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    void given_nothing_then_return_user_list_and_2xx() throws Exception {
        List<User> users = anyValidUserList();
        List<UserResponseModel> userResponseModels = anyValidUserResponseModelList();

        when(userService.findAll(ANY_PAGE, ANY_LIMIT)).thenReturn(users);
        when(userMapper.usersToUserResponseModels(users)).thenReturn(userResponseModels);

        mockMvc.perform(get(UserController.USER_CONTROLLER_ADDRESS)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(ANY_PAGE))
                        .param("limit", String.valueOf(ANY_LIMIT)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id").value(userResponseModels.get(0).getId()));
    }

    @Test
    void given_valid_update_password_model_then_return_2xx() throws Exception {
        UpdatePasswordModel updatePasswordModel = anyValidUpdatePasswordModel();

        doNothing().when(userService).updatePassword(ANY_USERNAME, updatePasswordModel);

        mockMvc.perform(patch(UserController.USER_CONTROLLER_ADDRESS + "/" + ANY_USERNAME + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(updatePasswordModel)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void given_invalid_update_password_model_then_return_4xx() throws Exception {
        UpdatePasswordModel updatePasswordModel = anyInvalidUpdatePasswordModel();

        mockMvc.perform(patch(UserController.USER_CONTROLLER_ADDRESS + "/" + ANY_USERNAME + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(updatePasswordModel)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}