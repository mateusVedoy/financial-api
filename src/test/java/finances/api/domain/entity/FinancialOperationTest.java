package finances.api.domain.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialOperationTest {

    @Test
    public void shouldInstantiateSuccessfullyThisWithoutId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ts = LocalDateTime.parse("2016-03-04 11:30:23", formatter);
        FinancialOperation financialOperation = new FinancialOperation("input", 1200, ts);
        assertTrue(financialOperation.isValid());
        assertNull(financialOperation.getId());
    }

    @Test
    public void shouldInstantiateSuccessfullyThisWithOperationTypeAsEntrance() {
        FinancialOperation financialOperation = new FinancialOperation( "input", 1200);
        assertTrue(financialOperation.isValid());
        assertEquals(financialOperation.getType(), "Entrance");
    }

    @Test
    public void shouldInstantiateSuccessfullyThisWithOperationTypeAsExit() {
        FinancialOperation financialOperation = new FinancialOperation( "output", 1200);
        assertTrue(financialOperation.isValid());
        assertEquals(financialOperation.getType(), "Exit");
    }

    @Test
    public void shouldInstantiateSuccessfullyThisWithId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ts = LocalDateTime.parse("2016-03-04 11:30:23", formatter);
        FinancialOperation financialOperation = new FinancialOperation(2L, 1L, 1200, ts);
        assertTrue(financialOperation.isValid());
        assertEquals(financialOperation.getId(), 2);
    }

    @Test
    public void shouldInstantiateSuccessfullyThisWithImplicitDateTime() {
        FinancialOperation financialOperation = new FinancialOperation( "output", 1200);
        assertTrue(financialOperation.isValid());
    }

    @Test
    public void shouldThrownValidationError() {
        FinancialOperation financialOperation = new FinancialOperation("in", -12, null);
        assertFalse(financialOperation.isValid());
    }
}