package finances.api.application.converter;

import finances.api.application.dto.FinancialStatementDTO;
import finances.api.domain.entity.FinancialStatement;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.domain.port.IConverter;
import org.springframework.stereotype.Component;

@Component
public class FinancialStatementConverter implements IConverter<FinancialStatementDTO, FinancialStatement> {
    @Override
    public FinancialStatementDTO convert(FinancialStatement obj) throws BusinessValidationError {
        return new FinancialStatementDTO(obj.getState(), obj.getBalance(), obj.getStartDate().toString(), obj.getFinalDate().toString());
    }
}
