package finances.api.domain.port;

import finances.api.application.dto.FinancialOperationDTO;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationException;

public interface IConverterDTO {
    FinancialOperation convert(FinancialOperationDTO dto) throws BusinessValidationException;
}
