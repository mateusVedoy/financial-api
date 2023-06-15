package finances.api.application.converter;

import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.domain.port.IConverter;
import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.springframework.stereotype.Component;

@Component
public class FinancialOperationModelConverter implements IConverter<FinancialOperation, FinancialOperationModel> {
    @Override
    public FinancialOperation convert(FinancialOperationModel model) throws BusinessValidationError {

        FinancialOperation operation = new FinancialOperation(
                model.getId(),
                model.getType(),
                model.getAmount(),
                model.getExecutedAt()
        );
        if(operation.isValid())
            return operation;
        else
            throw new BusinessValidationError(operation.getErrors());
    }
}
