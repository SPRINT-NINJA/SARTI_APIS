package mx.edu.utez.SARTI_APIS.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseApiException extends RuntimeException {
    public final HttpStatus status;

    public ResponseApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

