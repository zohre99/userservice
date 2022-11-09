package ir.surena.userservice.controller.mapper;

import ir.surena.userservice.controller.model.request.CreateUserModel;
import ir.surena.userservice.controller.model.response.UserResponseModel;
import ir.surena.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = {java.util.Date.class})
public interface UserMapper {

    @Mapping(target = "createdDate", expression = "java(new Date())")
    @Mapping(target = "modifiedDate", expression = "java(new Date())")
    User createUserModelToUser(CreateUserModel createUserModel);

    UserResponseModel userToUserResponseModel(User user);

    List<UserResponseModel> usersToUserResponseModels(List<User> users);

}
