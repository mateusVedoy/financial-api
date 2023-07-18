package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.dto.FinancialStatementDTO;
import finances.api.application.dto.OperationTypeDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.entity.FinancialStatement;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProduceFinancialStatementByPeriod {

    private static final String SUCCESS = "Financial statement by period bellow. ";
    private static final String EMPTY_SUCCESS = "There's no financial operation for period";
    private static final String ERROR = "Something went wrong. Consult errors.";
    @Autowired
    private FindFinancialOperationByPeriod findFinancialOperationByPeriod;
    @Autowired
    private FinancialOperationConverter converter;
    //TODO: rever essa parte do stament
    //TODO: aqui vou receber model, converter p dto e daí fazer o calc de balance
    public APIResponse produce(String initialDate, String finalDate) {

        FinancialStatement statement = new FinancialStatement(initialDate, finalDate);

        if(!statement.isValid())
            return new ResponseError(400, ERROR, statement.getErrors());

        //antes de buscar na base os dados, buscará em cache pelo periodo
         APIResponse response = findFinancialOperationByPeriod.findByPeriod(
                 statement.getStartDate(),
                 statement.getFinalDate()
         );

         if(isResponseError(response))
             return response;

         if(isEmptyFinancialOperationToCalculateBalance(response))
             return response;

     //TODO: gera o statement para salvar em base cache para novas buscas e devolve o dto
     //TODO: para gerar cache ter evento q sobe e um listener limpa cache

        //aqui gero o calc e devolvo p usuario o resultado

        try{
            List<FinancialOperationDTO> operationDTOS = convertList(response.content());
            double balance = setBalanceAmount(operationDTOS);
            statement.setBalance(balance);
            saveStatementInCache(statement);
        }catch(BusinessException exception){
            return new ResponseError(400, ERROR, exception);
        }
        FinancialStatementDTO statementDTO = financialStatementDTOConverter(statement);
        return new ResponseSuccess<FinancialStatementDTO>(200, SUCCESS, List.of(statementDTO));
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

    private boolean isEmptyFinancialOperationToCalculateBalance(APIResponse response) {
        return response.getMessage().equals(EMPTY_SUCCESS);
    }

    //TODO: criar classe p isso
    private FinancialStatementDTO financialStatementDTOConverter(FinancialStatement financialStatement) {
        return new FinancialStatementDTO(financialStatement.getState(), financialStatement.getBalance(), financialStatement.getStartDate().toString(), financialStatement.getFinalDate().toString());
    }

    private void saveStatementInCache(FinancialStatement statement) {
        //TODO: aqui depois gerar cache do dado
        //TODO: precisa haver maneira de limpar cache qnd houver novo registro para o periodo
        System.out.println(statement);
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

    private boolean isFinancialOperationTypeInput(FinancialOperationDTO financialOperation) {
        return financialOperation.getType().equals(OperationTypeDTO.IN.getValue());
    }

    private boolean isFinancialOperationListEmptyOrNull(List<FinancialOperationDTO> list) {
        return list == null || list.size() == 0;
    }
}
