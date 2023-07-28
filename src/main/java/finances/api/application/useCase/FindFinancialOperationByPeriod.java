package finances.api.application.useCase;

import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.response.StatusMessage;
import finances.api.domain.entity.FinancialOperation;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Cacheable(value = "cache")
public class FindFinancialOperationByPeriod {

    @Autowired
    private FinancialOperationRepository repository;

    public APIResponse findByPeriod(LocalDate initialDate, LocalDate finalDate) {

        try{
            List<FinancialOperation> operations = repository.findByPeriod(
                   initialDate,
                    finalDate
            );
            if(isOperationsPerPeriodEmpty(operations))
                return new ResponseSuccess<FinancialOperation>(200, StatusMessage.EMPTY_SUCCESS.getValue());
            else
                return new ResponseSuccess<FinancialOperation>(200, StatusMessage.SUCCESS.getValue(), operations);
        }catch (Exception ex) {
            return new ResponseError(400, StatusMessage.ERROR.getValue(), ex);
        }

    }

    private boolean isOperationsPerPeriodEmpty(List<FinancialOperation> operations){
        return operations.size() == 0;
    }
}
