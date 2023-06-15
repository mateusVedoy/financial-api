package finances.api.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialOperationDTOTest {

    @Test
    public void shouldInstantiateSuccessfullyThisOnlyWithTypeAndAmount() {
        FinancialOperationDTO dto = new FinancialOperationDTO("input", 1450.0);
        assertEquals(dto.getType(), "input");
        assertEquals(dto.getAmount(), 1450.0);
    }

    @Test
    public void shouldInstantiateSuccessfullyThis() {
        FinancialOperationDTO dto = new FinancialOperationDTO(
                "input",
                1450.0,
                LocalDate.parse("2023-07-09"),
                LocalTime.parse("11:09:11")
        );
        assertEquals(dto.getType(), "input");
        assertEquals(dto.getAmount(), 1450.0);
        assertEquals(dto.getDate(), LocalDate.parse("2023-07-09"));
        assertEquals(dto.getHour(), LocalTime.parse("11:09:11"));
    }

    @Test
    public void shouldInstantiateSuccessfullyThisWithId() {
        FinancialOperationDTO dto = new FinancialOperationDTO(
                2L,
                "input",
                1450.0,
                LocalDate.parse("2023-07-09"),
                LocalTime.parse("11:09:11")
        );
        assertEquals(dto.getId(), 2L);
        assertEquals(dto.getType(), "input");
        assertEquals(dto.getAmount(), 1450.0);
        assertEquals(dto.getDate(), LocalDate.parse("2023-07-09"));
        assertEquals(dto.getHour(), LocalTime.parse("11:09:11"));
    }

}