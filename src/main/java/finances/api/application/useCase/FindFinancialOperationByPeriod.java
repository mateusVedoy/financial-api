package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.response.StatusMessage;
import finances.api.application.service.DateChronologyValidator;
import finances.api.application.service.DatePatternValidator;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO: implementar cache
@Component
public class FindFinancialOperationByPeriod extends FinancialOperationAbstract {

    @Autowired
    private DatePatternValidator datePatternValidator;
    @Autowired
    private DateChronologyValidator dateChronologyValidator;

    public APIResponse findByPeriod(LocalDate initialDate, LocalDate finalDate) {

        if(!isDatePatternValid(initialDate, finalDate))
            return new ResponseError(400, StatusMessage.ERROR.getValue(), new BusinessException("Cannot cast wrong dates format. Required pattern YYYY-MM-DD", "initialDate.and.finalDate"));

        if(!isDateChronologyValid(initialDate, finalDate))
            return new ResponseError(400, StatusMessage.ERROR.getValue(), new BusinessException("Final date cannot be less than initial date", "initialDate.and.finalDate"));

        try{
            List<FinancialOperation> operations = repository.findByPeriod(
                   initialDate,
                    finalDate
            );
            if(isOperationsPerPeriodEmpty(operations))
                return new ResponseSuccess<>(200, StatusMessage.EMPTY_SUCCESS.getValue());
            else
                return buildAPIResponseWithData(convertList(operations));
        }catch (Exception ex) {
            return new ResponseError(400, StatusMessage.ERROR.getValue(), ex);
        }
    }
    private APIResponse buildAPIResponseWithData(List<FinancialOperationDTO> list) {
        return new ResponseSuccess<FinancialOperationDTO>(200, StatusMessage.SUCCESS.getValue(), list);
    }

    private boolean isDatePatternValid(LocalDate initialDate, LocalDate finalDate) {
        if(isDateEmpty(initialDate, finalDate))
            return false;

        return datePatternValidator.isValid(
                List.of(
                        initialDate.toString(),
                        finalDate.toString())
        );
    }
    private boolean isDateChronologyValid(LocalDate initialDate, LocalDate finalDate) {
        if(isDateEmpty(initialDate, finalDate))
            return false;

        return dateChronologyValidator.isValid(
                List.of(
                        initialDate,
                        finalDate)
        );
    }

    private boolean isDateEmpty(LocalDate initialDate, LocalDate finalDate) {
        return initialDate == null || finalDate == null;
    }
    private boolean isOperationsPerPeriodEmpty(List<FinancialOperation> operations){
        return operations.size() == 0;
    }
}
