package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.response.StatusMessage;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Cacheable(value = "cache")
public class FindAllFinancialOperation {
    @Autowired
    private FinancialOperationRepository repository;

    @Autowired
    private FinancialOperationConverter converter;

    public APIResponse findAll() {
        try{
            List<FinancialOperation> list = repository.findAll();
            if(!isThereAnyValueToBeSolved(list))
                return new ResponseSuccess<>(200, StatusMessage.EMPTY_SUCCESS.getValue());
            else
                return buildAPIResponseWithData(convertList(list));
        }catch (Exception ex) {
            return new ResponseError(400, StatusMessage.ERROR.getValue(), ex);
        }
    }

    private APIResponse buildAPIResponseWithData(List<FinancialOperationDTO> list) {
        return new ResponseSuccess<FinancialOperationDTO>(200, StatusMessage.SUCCESS.getValue(), list);
    }
    private List<FinancialOperationDTO> convertList(List<FinancialOperation> list) {
        List<FinancialOperationDTO> operationList = new ArrayList<>();
        list.forEach(value -> {
            try {
                operationList.add(converter.convert(value));
            } catch (BusinessValidationError e) {
                throw new BusinessException("Error during conversion process", "convert.entity.to.dto");
            }
        });
        return operationList;
    }
    private boolean isThereAnyValueToBeSolved(List<FinancialOperation> list) {
        return list.size() > 0;
    }
}
