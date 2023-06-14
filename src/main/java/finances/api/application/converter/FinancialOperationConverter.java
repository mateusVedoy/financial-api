package finances.api.application.converter;


import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.dto.OperationTypeDTO;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.entity.OperationType;
import finances.api.domain.exception.BusinessValidationException;
import finances.api.domain.port.IConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class FinancialOperationConverter implements IConverter<FinancialOperationDTO, FinancialOperation> {

    @Override
    public FinancialOperationDTO convert(FinancialOperation obj) throws BusinessValidationException {
        return new FinancialOperationDTO(
                obj.getId(),
                setType(obj.getType()),
                obj.getAmount(),
                resolveDate(obj.getExecutedAt()),
                resolveTime(obj.getExecutedAt())
        );
    }

    private String setType(String type) {
        if(type.equals(OperationType.IN.getValue()))
            return OperationTypeDTO.IN.getValue();
        else if(type.equals(OperationType.OUT.getValue()))
            return OperationTypeDTO.OUT.getValue();
        else
            return type;
    }

    private LocalDate resolveDate(LocalDateTime ts) {
        return ts.toLocalDate();
    }

    private LocalTime resolveTime(LocalDateTime ts) {
        return ts.toLocalTime();
    }
}
