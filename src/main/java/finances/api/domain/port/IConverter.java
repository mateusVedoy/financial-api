package finances.api.domain.port;

import finances.api.application.dto.FinancialOperationDTO;
import finances.api.domain.entity.FinancialOperation;

public interface IConverter {
    FinancialOperationDTO convert(FinancialOperation model);
}
