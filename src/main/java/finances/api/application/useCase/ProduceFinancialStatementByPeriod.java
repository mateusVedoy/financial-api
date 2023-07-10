package finances.api.application.useCase;

import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.domain.entity.FinancialStatement;
import finances.api.domain.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: implementar
public class ProduceFinancialStatementByPeriod {
    @Autowired
    private FindFinancialOperationByPeriod findFinancialOperationByPeriod;

    public APIResponse produce(String initialDate, String finalDate) {
         APIResponse statement = findFinancialOperationByPeriod.findByPeriod(initialDate, finalDate);
    }

}
