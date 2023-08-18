package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.response.StatusMessage;
import finances.api.application.service.CachingService;
import finances.api.application.service.SerializationService;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindAllFinancialOperation extends FinancialOperationAbstract {
    private static final String CACHE_KEY= "FIND_ALL";

    public FindAllFinancialOperation() {}

    public APIResponse findAll() {

        try{
            if(hasCache(CACHE_KEY)){
                byte[] bytes = getCache(CACHE_KEY);
                List<FinancialOperationDTO> dto = deserialize(bytes);
                return new ResponseSuccess<FinancialOperationDTO>(
                        200,
                        StatusMessage.SUCCESS.getValue(),
                        dto
                );
            }

            List<FinancialOperation> list = repository.findAll();
            return buildResponse(list);
        }catch (Exception ex) {
            return new ResponseError(400, StatusMessage.ERROR.getValue(), ex);
        }
    }

    private APIResponse buildResponse(List<FinancialOperation> list) {
        if(!isThereAnyValueToBeSolved(list)){
            return new ResponseSuccess<>(200, StatusMessage.EMPTY_SUCCESS.getValue());
        } else {
            List<FinancialOperationDTO> financialOperationDTOList = convertList(list);
            setCache(CACHE_KEY, financialOperationDTOList);
            return new ResponseSuccess<FinancialOperationDTO>(
                    200,
                    StatusMessage.SUCCESS.getValue(),
                    financialOperationDTOList
            );
        }
    }


    private boolean isThereAnyValueToBeSolved(List<FinancialOperation> list) {
        return !list.isEmpty();
    }
}
