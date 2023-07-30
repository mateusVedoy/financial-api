package finances.api.infraestructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;

public class HTTPResponseEntity<T> extends ResponseEntity<T> implements Serializable {
    public HTTPResponseEntity(HttpStatus status) {
        super(status);
    }

    public HTTPResponseEntity(T body, HttpStatus status) {
        super(body, status);
    }

    public HTTPResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public HTTPResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public HTTPResponseEntity(T body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }
}
