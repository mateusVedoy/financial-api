package finances.api.application.converter;

import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialOperationModelConverterTest {

    FinancialOperationModelConverter converter = new FinancialOperationModelConverter();

    @Test
    public void shouldConvertSuccessfullyDBModelToEntity() throws BusinessValidationError {
        Date date = new Date();
        LocalDateTime ts = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withNano(0);

        FinancialOperationModel model = new FinancialOperationModel(
                2L,
                1L,
                350,
                ts
        );

        FinancialOperation entity = converter.convert(model);
        assertTrue(entity.isValid());
        assertEquals(entity.getId(), 2L);
    }

    @Test
    public void shouldThrowBusinessValidationError() {
        FinancialOperationModel model = new FinancialOperationModel(
                2L,
                1L,
                350,
                null
        );

        assertThrows(BusinessValidationError.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                converter.convert(model);
            }
        });
    }
}