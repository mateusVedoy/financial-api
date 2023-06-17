package finances.api.application.response;

import finances.api.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

public class ResponseErrorTest {

    BusinessException first_error = new BusinessException("first error", "first.error");
    BusinessException second_error = new BusinessException("second error", "second.error");

    @Test
    public void shouldInstantiateSuccessfullyResponseErrorWithBusinessException() {
        List<BusinessException> errors = new ArrayList<>(List.of(
                first_error,
                second_error
        ));
        ResponseError response = new ResponseError(400, "Error occurred", errors);
        assertEquals(response.getErrors().size(), 2);
        assertEquals(response.getStatus(), 400);
    }

    @Test
    public void shouldInstantiateSuccessfullyResponseErrorWithAnyException() {
        RuntimeException ex = new RuntimeException("error mocked");
        ResponseError response = new ResponseError(400, "Error occurred", ex);
        assertEquals(response.getErrors().size(), 1);
        assertEquals(response.getStatus(), 400);
    }
}