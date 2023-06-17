package finances.api.application.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void shouldInstantiateMessageSuccessfully() {
        Message message = new Message("message to be sent", "stacktrace.til.property");
        assertEquals(message.getMessage(), "message to be sent");
        assertEquals(message.getStacktrace(), "stacktrace.til.property");
        assertInstanceOf(Message.class, message);
    }
}