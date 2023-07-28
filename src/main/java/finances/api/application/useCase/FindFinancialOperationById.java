package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.response.StatusMessage;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@Cacheable(value = "operationById")
public class FindFinancialOperationById {
    @Autowired
    private FinancialOperationRepository repository;

    @Autowired
    private FinancialOperationConverter converter;

    public APIResponse findById(Long id) {
        try{
             FinancialOperationDTO dto = converter.convert(
                    repository.findById(id)
            );
             return new ResponseSuccess<>(200, StatusMessage.SUCCESS.getValue(), List.of(dto));
        }catch (NoSuchElementException ex) {
            return new ResponseSuccess<>(200, StatusMessage.EMPTY_SUCCESS.getValue(), List.of());
        }catch (Exception ex) {
            return new ResponseError(400, StatusMessage.ERROR.getValue(), ex);
        }
    }
}
