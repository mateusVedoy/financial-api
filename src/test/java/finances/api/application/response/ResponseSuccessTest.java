package finances.api.application.response;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseSuccessTest {

    @Test
    public void shouldInstantiateSuccessfullyThisWithoutData() {
        ResponseSuccess<String> response = new ResponseSuccess<>(201, "Action scheduled successfully");
        assertEquals(response.getStatus(), 201);
        assertEquals(response.getMessage(), "Action scheduled successfully");
        assertNull(response.getData());
    }

    @Test
    public void shouldInstantiateSuccessfullyThisWithData() {

        List<String> data = new ArrayList<>(List.of(
                "first value",
                "second value",
                "third value"
        ));

        ResponseSuccess<String> response = new ResponseSuccess<>(
                201,
                "Action scheduled successfully",
                data
        );

        assertEquals(response.getData(),data);
    }
}