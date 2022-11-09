package ir.surena.userservice.controller.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "UserResponseModel", description = "Represents user information.")
public class UserResponseModel {

    @Schema(description = "The ID of the entity", example = "1")
    private Long id;

    @Schema(description = "User unique name", example = "j.doe")
    private String username;

    @Schema(description = "User first name", example = "John")
    private String firstName;

    @Schema(description = "User last name", example = "Doe")
    private String lastName;

    @Schema(description = "The date of when user was created.", example = "2022-11-09T15:50:46.852Z")
    private Date createdDate;

    @Schema(description = "The date of when user was modified.", example = "2022-11-09T15:50:46.852Z")
    private Date modifiedDate;

}
