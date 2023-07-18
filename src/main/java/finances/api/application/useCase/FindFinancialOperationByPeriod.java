package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.entity.FinancialStatement;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FindFinancialOperationByPeriod {

    private static final String EMPTY_SUCCESS = "There's no financial operation for period";
    private static final String SUCCESS = "Financial operation fetched bellow";
    private static final String ERROR = "Something went wrong. Consult errors.";

    @Autowired
    private FinancialOperationRepository repository;

    public APIResponse findByPeriod(LocalDate initialDate, LocalDate finalDate) {

        try{
            List<FinancialOperation> operations = repository.findByPeriod(
                   initialDate,
                    finalDate
            );
            if(isOperationsPerPeriodEmpty(operations))
                return new ResponseSuccess<FinancialOperation>(200,EMPTY_SUCCESS);
            else
                return new ResponseSuccess<FinancialOperation>(200, SUCCESS, operations);
        }catch (Exception ex) {
            return new ResponseError(400, ERROR, ex);
        }

    }

    private boolean isOperationsPerPeriodEmpty(List<FinancialOperation> operations){
        return operations.size() == 0;
    }
}
