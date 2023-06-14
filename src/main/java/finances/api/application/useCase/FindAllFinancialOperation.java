package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseSuccess;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationException;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindAllFinancialOperation {
    @Autowired
    private FinancialOperationRepository repository;

    @Autowired
    private FinancialOperationConverter converter;

    public APIResponse findAll() {
        List<FinancialOperation> list = repository.findAll();
        if(!isThereAnyValueToBeSolved(list))
            return new ResponseSuccess<>(200,"There's no financial operation to be recovered");
        else
            return buildAPIResponseWithData(convertList(list));
    }

    private APIResponse buildAPIResponseWithData(List<FinancialOperationDTO> list) {
        return new ResponseSuccess<FinancialOperationDTO>(200, "Financial operation fetched bellow", list);
    }
    private List<FinancialOperationDTO> convertList(List<FinancialOperation> list) {
        List<FinancialOperationDTO> operationList = new ArrayList<>();
        list.forEach(value -> {
            try {
                operationList.add(converter.convert(value));
            } catch (BusinessValidationException e) {
                throw new RuntimeException(e); //ajustar depois o tipo de erro
            }
        });
        return operationList;
    }
    private boolean isThereAnyValueToBeSolved(List<FinancialOperation> list) {
        return list.size() > 0;
    }
}
