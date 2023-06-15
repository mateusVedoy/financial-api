package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationDTOConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateFinancialOperation {

    private static final String SUCCESS = "Financial Operation saved successfully.";
    private static final String ERROR = "Something went wrong. Consult errors.";
    @Autowired
    private FinancialOperationDTOConverter converter;
    @Autowired
    private FinancialOperationRepository repository;

    public APIResponse save(FinancialOperationDTO dto){
        try{
            FinancialOperation operation = converter.convert(dto);
            repository.save(operation);
            return new ResponseSuccess<>(201, SUCCESS);
        }catch(BusinessValidationError error) {
            return new ResponseError(400, ERROR, error.getErrors());
        }
    }
}
