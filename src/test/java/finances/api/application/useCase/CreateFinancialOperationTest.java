package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationDTOConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateFinancialOperationTest {

    @InjectMocks
    CreateFinancialOperation create = new CreateFinancialOperation();
    @Mock
    FinancialOperationDTOConverter converter = new FinancialOperationDTOConverter();
    @Mock
    FinancialOperationRepository repository = new FinancialOperationRepository();

    FinancialOperationDTO dto = new FinancialOperationDTO();

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldCallConverterOnce() throws BusinessValidationError {
        create.save(dto);
        verify(converter, times(1)).convert(dto);
    }

    @Test
    public void shouldCallRepositoryOnce() throws BusinessValidationError {
        given(converter.convert(dto)).willReturn(new FinancialOperation());
        create.save(dto);
        verify(repository, times(1)).save(any(FinancialOperation.class));
    }

    @Test
    public void shouldReturnResponseSuccessWhenExecuteSuccessfully() throws BusinessValidationError {
        given(converter.convert(dto)).willReturn(new FinancialOperation());
        APIResponse response =  create.save(dto);
        assertEquals(response.getStatus(), 201);
        assertEquals(response.getMessage(), "Financial Operation saved successfully.");
    }

    @Test
    public void shouldReturnResponseErrorWhenExecuteWithBusinessError() throws BusinessValidationError {

        List<BusinessException> errors = new ArrayList<>(List.of(
                new BusinessException("error one", "error.one"),
                new BusinessException("error two", "error.two")
        ));

        given(converter.convert(dto)).willThrow(new BusinessValidationError(errors));
        APIResponse response = create.save(dto);
        assertEquals(response.getStatus(), 400);
        assertEquals(response.getMessage(), "Something went wrong. Consult errors.");
    }

    @Test
    public void shouldReturnResponseErrorWhenExecuteWithAnyError() throws BusinessValidationError {

        List<BusinessException> errors = new ArrayList<>(List.of(
                new BusinessException("error one", "error.one"),
                new BusinessException("error two", "error.two")
        ));

        given(converter.convert(dto)).willThrow(new RuntimeException("fake error"));
        APIResponse response = create.save(dto);
        assertEquals(response.getStatus(), 400);
        assertEquals(response.getMessage(), "Something went wrong. Consult errors.");
    }
}