package finances.api.application.converter;

import finances.api.application.dto.FinancialOperationDTO;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationException;
import finances.api.domain.port.IConverterDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class FinancialOperationDTOConverter implements IConverterDTO {
    @Override
    public FinancialOperation convert(FinancialOperationDTO dto) throws BusinessValidationException {
        String type = dto.getType();
        double amount = this.getAmount(dto);
        LocalDateTime date = this.getDateAndHour(dto);
        FinancialOperation operation = new FinancialOperation(type, amount, date);
        if(operation.isValid())
            return operation;
        else
            throw new BusinessValidationException(operation.getErrors());
    }
    private double getAmount(FinancialOperationDTO dto) {
        return dto.getAmount();
    }
    private LocalDateTime getDateAndHour(FinancialOperationDTO dto) {
        LocalDate date = dto.getDate();
        LocalTime hour = dto.getHour();
        if(date == null || hour == null)
            return null;
        return LocalDateTime.parse(date.toString()+'T'+hour.toString());
    }
}
