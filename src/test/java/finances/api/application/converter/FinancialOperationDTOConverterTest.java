package finances.api.application.converter;

import finances.api.application.dto.FinancialOperationDTO;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialOperationDTOConverterTest {

    FinancialOperationDTOConverter converter = new FinancialOperationDTOConverter();

    @Test
    public void shouldConvertSuccessfullyDTOToEntity() {
        FinancialOperationDTO dto = new FinancialOperationDTO(
                "input",
                1450.0,
                LocalDate.parse("2023-07-09"),
                LocalTime.parse("11:09:11")
        );
        try{
            FinancialOperation entity = converter.convert(dto);
            assertTrue(entity.isValid());
            assertEquals(entity.getType(), "Entrance");
            assertEquals(entity.getErrors().size(), 0);
            assertEquals(entity.getExecutedAt().toString(), "2023-07-09T11:09:11");
        }catch(BusinessValidationError e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void shouldThrowErrorTryingToConvertDTOToEntity() {
        FinancialOperationDTO dto = new FinancialOperationDTO(
                "xpto",
                -2,
                LocalDate.parse("2023-07-09"),
                null
        );
        assertThrows(BusinessValidationError.class, new Executable() {
           @Override
           public void execute() throws Throwable {
               converter.convert(dto);
           }
        });
    }
}