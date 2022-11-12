package ir.surena.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserExceptionModel> handleException(UserException userException) {
        UserExceptionModel exceptionModel = new UserExceptionModel(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                userException.getExceptionCode(),
                userException.getMessage());
        return new ResponseEntity<>(exceptionModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
