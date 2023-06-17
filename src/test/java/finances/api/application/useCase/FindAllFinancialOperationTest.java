package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.response.APIResponse;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

public class FindAllFinancialOperationTest {

    @InjectMocks
    FindAllFinancialOperation findAll = new FindAllFinancialOperation();
    @Mock
    FinancialOperationRepository repository = new FinancialOperationRepository();
    @Mock
    FinancialOperationConverter converter = new FinancialOperationConverter();

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldResponseSuccessfullyWithNoDataWhenHasNoInfoIntoDB() {
        given(repository.findAll()).willReturn(new ArrayList<FinancialOperation>());
        APIResponse response = findAll.findAll();
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getMessage(), "There's no financial operation to be recovered");
    }

    @Test
    public void shouldResponseSuccessfullyWithNoDataWhenHasInfoIntoDB() {
        given(repository.findAll()).willReturn(new ArrayList<FinancialOperation>(List.of(
                new FinancialOperation(),
                new FinancialOperation()
        )));
        APIResponse response = findAll.findAll();
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getMessage(), "Financial operation fetched bellow");
    }

    @Test
    public void shouldResponseErrorWhenGotBusinessException() throws BusinessValidationError {
        given(repository.findAll()).willReturn(new ArrayList<FinancialOperation>(List.of(
                new FinancialOperation(),
                new FinancialOperation()
        )));
        given(converter.convert(any(FinancialOperation.class))).willThrow(new BusinessException("error faked", "faked.error"));
        APIResponse response = findAll.findAll();
        assertEquals(response.getStatus(), 400);
        assertEquals(response.getMessage(), "Something went wrong. Consult errors.");
    }
}