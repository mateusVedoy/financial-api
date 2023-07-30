package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.converter.FinancialStatementConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.dto.FinancialStatementDTO;
import finances.api.application.dto.FinancialStatementTotalDTO;
import finances.api.application.dto.OperationTypeDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.response.StatusMessage;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.entity.FinancialStatement;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProduceFinancialStatementByPeriod {
    @Autowired
    private FindFinancialOperationByPeriod findFinancialOperationByPeriod;
    @Autowired
    private FinancialStatementConverter statementConverter;

    public APIResponse produce(String initialDate, String finalDate) {

        FinancialStatement statement = new FinancialStatement(initialDate, finalDate);

        if(!statement.isValid())
            return new ResponseError(400, StatusMessage.ERROR.getValue(), statement.getErrors());

         APIResponse response = findFinancialOperationByPeriod.findByPeriod(
                 statement.getStartDate(),
                 statement.getFinalDate()
         );

         if(isResponseError(response))
             return response;

         if(isEmptyFinancialOperationToCalculateBalance(response))
             return response;

        try{
            List<FinancialOperationDTO> operationDTOS = response.content();
            double balance = setBalanceAmount(operationDTOS);
            double totalInputs = setTotalInputAmount(operationDTOS);
            double totalOutputs = setTotalOutputAmount(operationDTOS);
            statement.setBalance(balance);
            statement.setTotalInputAmount(totalInputs);
            statement.setTotalOutputAmount(totalOutputs);
        }catch(BusinessException exception){
            return new ResponseError(400, StatusMessage.ERROR.getValue(), exception);
        }
        FinancialStatementDTO statementDTO = financialStatementDTOConverter(statement);
        return new ResponseSuccess<FinancialStatementDTO>(200, StatusMessage.SUCCESS.getValue(), List.of(statementDTO));
    }

    private boolean isEmptyFinancialOperationToCalculateBalance(APIResponse response) {
        return response.getMessage().equals(StatusMessage.EMPTY_SUCCESS.getValue());
    }

    private FinancialStatementDTO financialStatementDTOConverter(FinancialStatement financialStatement) {

        try{
            return statementConverter.convert(financialStatement);
        }catch (BusinessValidationError error) {
            throw new BusinessException("Error during converting financial statement to its dto", "financialStatement");
        }
    }

    private boolean isResponseError(APIResponse response){
        return response.getStatus() == 400;
    }

    private double setBalanceAmount(List<FinancialOperationDTO> financialOperations) {
        if(isFinancialOperationListEmptyOrNull(financialOperations))
            return 0;
        double amount = 0;
        for (FinancialOperationDTO operation: financialOperations) {
            if(isFinancialOperationTypeInput(operation))
                amount += operation.getAmount();
            else
                amount -= operation.getAmount();
        }
        return amount;
    }

    private double setTotalInputAmount(List<FinancialOperationDTO> financialOperations) {
        if(isFinancialOperationListEmptyOrNull(financialOperations))
            return 0;
        double amount = 0;
        for (FinancialOperationDTO operation: financialOperations) {
            if(isFinancialOperationTypeInput(operation))
                amount += operation.getAmount();
        }
        return amount;
    }
    private double setTotalOutputAmount(List<FinancialOperationDTO> financialOperations) {
        if(isFinancialOperationListEmptyOrNull(financialOperations))
            return 0;
        double amount = 0;
        for (FinancialOperationDTO operation: financialOperations) {
            if(!isFinancialOperationTypeInput(operation))
                amount += operation.getAmount();
        }
        return amount;
    }

    private boolean isFinancialOperationTypeInput(FinancialOperationDTO financialOperation) {
        return financialOperation.getType().equals(OperationTypeDTO.IN.getValue());
    }

    private boolean isFinancialOperationListEmptyOrNull(List<FinancialOperationDTO> list) {
        return list == null || list.size() == 0;
    }
}
