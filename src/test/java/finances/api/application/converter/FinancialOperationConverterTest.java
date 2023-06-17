package finances.api.application.converter;

import finances.api.application.dto.FinancialOperationDTO;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialOperationConverterTest {

    FinancialOperationConverter converter = new FinancialOperationConverter();

    @Test
    public void shouldConvertSuccessfullyEntityToDTO() throws BusinessValidationError {
        FinancialOperation entity = new FinancialOperation(
                "input",
                10
        );
        FinancialOperationDTO dto = converter.convert(entity);
        assertEquals(dto.getType(), "input");
        assertEquals(dto.getAmount(), 10);
    }
}