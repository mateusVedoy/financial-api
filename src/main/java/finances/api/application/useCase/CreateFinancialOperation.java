package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationDTOConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.response.StatusMessage;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component

public class CreateFinancialOperation {
    @Autowired
    private FinancialOperationDTOConverter converter;
    @Autowired
    private FinancialOperationRepository repository;

    public APIResponse save(FinancialOperationDTO dto){
        try{
            FinancialOperation operation = converter.convert(dto);
            repository.save(operation);
            return new ResponseSuccess<>(201, StatusMessage.CREATED.getValue());
        }catch(BusinessValidationError error) {
            return new ResponseError(400, StatusMessage.ERROR.getValue(), error.getErrors());
        }catch (Exception ex) {
            return new ResponseError(400, StatusMessage.ERROR.getValue(), ex);
        }
    }
}
