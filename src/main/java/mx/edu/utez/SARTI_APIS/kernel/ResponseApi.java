package mx.edu.utez.SARTI_APIS.kernel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApi<T> {
    private T data;
    private HttpStatus status;
    private boolean error;
    private String message;
}
