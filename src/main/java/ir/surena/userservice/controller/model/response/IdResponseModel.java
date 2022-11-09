package ir.surena.userservice.controller.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "IdResponseModel", description = "Contains a single ID of an entity.")
public class IdResponseModel {

    @Schema(description = "The ID of an entity", example = "1")
    private Long id;

}
