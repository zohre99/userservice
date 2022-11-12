package ir.surena.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class UserExceptionModel {

    private int status;
    private Date timestamp;
    private String errorCode;
    private String errorMessage;

}
