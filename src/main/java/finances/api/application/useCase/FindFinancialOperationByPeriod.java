package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.domain.entity.FinancialOperation;
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

    private static final String EMPTY_SUCCESS = "There's no financial operation to be recovered";
    private static final String SUCCESS = "Financial operation fetched bellow";
    private static final String ERROR = "Something went wrong. Consult errors.";

    @Autowired
    private FinancialOperationRepository repository;
    @Autowired
    private FinancialOperationConverter converter;

    public APIResponse findByPeriod(String initialDate, String finalDate) {

        if(!isDatesAbleToBeAPeriod(initialDate, finalDate))
            return new ResponseError(400, ERROR, new BusinessException("Cannot cast wrong dates format. required pattern YYYY-MM-DD", "financialStatement.dates"));

        try{
            List<FinancialOperation> operations = repository.findByPeriod(initialDate, finalDate);
            if(isOperationsPerPeriodEmpty(operations))
                return new ResponseSuccess<>(200,EMPTY_SUCCESS);
            else
                return buildAPIResponseWithData(convertList(operations));
        }catch (Exception ex) {
            return new ResponseError(400, ERROR, ex);
        }

    }

    private boolean isOperationsPerPeriodEmpty(List<FinancialOperation> operations){
        return operations.size() == 0;
    }

    private APIResponse buildAPIResponseWithData(List<FinancialOperationDTO> list) {
        return new ResponseSuccess<FinancialOperationDTO>(200, SUCCESS, list);
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

    private boolean wasDatesToPeriodInformedCorrectly(String initialDate, String finalDate) {
        Pattern pattern = Pattern.compile("/[0-9]{4}(-[0-9]{2}){2}/");
        Matcher initialDateMatcher = pattern.matcher(initialDate);
        Matcher finalDateMatcher = pattern.matcher(finalDate);
        return initialDateMatcher.matches() && finalDateMatcher.matches();
    }

    private boolean isDatesAbleToBeAPeriod(String initialDate, String finalDate) {
        if(wasDatesToPeriodInformedCorrectly(initialDate, finalDate)){
            LocalDate initialDt = LocalDate.parse(initialDate);
            LocalDate finalDt = LocalDate.parse(finalDate);
            return finalDt.isAfter(initialDt);
        }
        return false;
    }
}
