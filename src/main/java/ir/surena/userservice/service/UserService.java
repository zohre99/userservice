package ir.surena.userservice.service;

import ir.surena.userservice.controller.model.request.UpdatePasswordModel;
import ir.surena.userservice.controller.model.request.UpdateUserModel;
import ir.surena.userservice.entity.User;
import ir.surena.userservice.exception.UserException;
import ir.surena.userservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ir.surena.userservice.exception.ExceptionMessage.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long create(User user) throws UserException {
        Optional<User> savedUser = userRepository.findByUsername(user.getUsername());
        if (savedUser.isPresent()) {
            throw new UserException(USERNAME_IS_ALREADY_EXISTS, USERNAME_IS_ALREADY_EXISTS_MSG);
        }
        return userRepository.save(user).getId();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND, USER_NOT_FOUND_MSG));
    }

    @Transactional
    public void deleteByUsername(String username) throws UserException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserException(USER_NOT_FOUND, USER_NOT_FOUND_MSG);
        }
        userRepository.deleteByUsername(username);
    }

    @Transactional
    public void deleteById(Long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException(USER_NOT_FOUND, USER_NOT_FOUND_MSG);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public Long updateByUsername(String username, UpdateUserModel updateUserModel) throws UserException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND, USER_NOT_FOUND_MSG));
        if (StringUtils.hasText(updateUserModel.getFirstName())) {
            user.setFirstName(updateUserModel.getFirstName());
        }
        if (StringUtils.hasText(updateUserModel.getLastName())) {
            user.setLastName(updateUserModel.getLastName());
        }
        user.setModifiedDate(new Date());

        userRepository.save(user);
        return user.getId();
    }

    public List<User> findAll(int page, int limit) {
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        Page<User> users = userRepository.findAll(pageable);
        return users.toList();
    }

    public void updatePassword(String username, UpdatePasswordModel updatePasswordModel) throws UserException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND, USER_NOT_FOUND_MSG));
        if (user.getPassword().equals(updatePasswordModel.getCurrentPassword())) {
            user.setPassword(updatePasswordModel.getNewPassword());
            user.setModifiedDate(new Date());
            userRepository.save(user);
        } else {
            throw new UserException(INCORRECT_PASSWORD, INCORRECT_PASSWORD_MSG);
        }
    }

}
