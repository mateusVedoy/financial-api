package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class FindFinancialOperationById {

    private static final String SUCCESS = "Financial operation fetched bellow";
    private static final String EMPTY_SUCCESS = "There's no data to be fetched";
    private static final String ERROR = "Something went wrong. Consult errors.";

    @Autowired
    private FinancialOperationRepository repository;

    @Autowired
    private FinancialOperationConverter converter;

    public APIResponse findById(Long id) {
        try{
             FinancialOperationDTO dto = converter.convert(
                    repository.findById(id)
            );
             return new ResponseSuccess<>(200, SUCCESS, List.of(dto));
        }catch (NoSuchElementException ex) {
            return new ResponseSuccess<>(200, EMPTY_SUCCESS, List.of());
        }catch (Exception ex) {
            return new ResponseError(400, ERROR, ex);
        }
    }
}
