package ir.surena.userservice.service;

import ir.surena.userservice.controller.model.request.UpdatePasswordModel;
import ir.surena.userservice.controller.model.request.UpdateUserModel;
import ir.surena.userservice.entity.User;
import ir.surena.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static ir.surena.userservice.MotherObject.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void given_valid_user_then_return_user_id() {
        User user = anyValidUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        Long id = userService.create(user);

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getId(), id);
    }

    @Test
    void given_valid_user_then_return_user_info() {
        Optional<User> optionalUser = Optional.of(anyValidUser());
        when(userRepository.findByUsername(ANY_USERNAME)).thenReturn(optionalUser);

        User user = userService.findByUsername(ANY_USERNAME);

        verify(userRepository, times(1)).findByUsername(ANY_USERNAME);
        assertEquals(ANY_ID, user.getId());
    }

    @Test
    void given_valid_username_then_return_nothing() {
        User user = anyValidUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteByUsername(user.getUsername());

        userService.deleteByUsername(user.getUsername());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).deleteByUsername(user.getUsername());
    }

    @Test
    void given_valid_id_then_return_nothing() {
        User user = anyValidUser();
        when(userRepository.findById(ANY_ID)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(ANY_ID);

        userService.deleteById(ANY_ID);

        verify(userRepository, times(1)).findById(ANY_ID);
        verify(userRepository, times(1)).deleteById(ANY_ID);
    }

    @Test
    void given_update_user_model_then_return_id() {
        User user = anyValidUser();
        User updatedUser = anyValidUser();
        UpdateUserModel updateUserModel = anyUpdateUserModel();
        updatedUser.setFirstName(updateUserModel.getFirstName());
        updatedUser.setLastName(updateUserModel.getLastName());

        when(userRepository.findByUsername(ANY_USERNAME)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUser);

        Long id = userService.updateByUsername(ANY_USERNAME, updateUserModel);

        verify(userRepository, times(1)).findByUsername(ANY_USERNAME);
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getId(), id);
    }

    @Test
    void given_page_info_then_return_user_list() {
        List<User> users = anyValidUserList();
        Page<User> userPage = new PageImpl<>(users);
        Pageable pageable = Pageable.ofSize(ANY_LIMIT).withPage(ANY_PAGE);

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        List<User> fetchedUsers = userService.findAll(ANY_PAGE, ANY_LIMIT);

        verify(userRepository, times(1)).findAll(pageable);
        assertEquals(users.size(), fetchedUsers.size());
    }

    @Test
    void given_update_password_model_then_return_nothing() {
        UpdatePasswordModel updatePasswordModel = anyValidUpdatePasswordModel();
        User user = anyValidUser();
        User updatedUser = anyValidUser();
        updatedUser.setPassword(updatePasswordModel.getNewPassword());

        when(userRepository.findByUsername(ANY_USERNAME)).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        userService.updatePassword(ANY_USERNAME, updatePasswordModel);

        verify(userRepository, times(1)).findByUsername(ANY_USERNAME);
        verify(userRepository, times(1)).save(updatedUser);
    }
}