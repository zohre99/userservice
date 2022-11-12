package ir.surena.userservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
public class UserException extends RuntimeException {

    private String exceptionCode;

    public UserException(String exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public UserException() {

    }

}
