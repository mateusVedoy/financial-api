package finances.api.application.useCase;

import finances.api.application.response.APIResponse;
import finances.api.domain.entity.FinancialOperation;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

public class FindFinancialOperationByPeriodTest {

    @InjectMocks
    private FindFinancialOperationByPeriod findFinancialOperationByPeriod;
    @Mock
    private FinancialOperationRepository repository;

    LocalDate initialDate = LocalDate.parse("2023-09-09");
    LocalDate finalDate = LocalDate.parse("2023-10-09");

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldReturnAListOfFinancialOperation() {
        List<FinancialOperation> operations = List.of(
                new FinancialOperation(),
                new FinancialOperation(),
                new FinancialOperation()
        );
        given(repository.findByPeriod(any(LocalDate.class), any(LocalDate.class))).willReturn(operations);
        APIResponse response = findFinancialOperationByPeriod.findByPeriod(initialDate, finalDate);
        assertEquals(response.content(), operations);
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getMessage(), "Financial Operation fetched bellow.");
    }

    @Test
    public void shouldReturnAnEmptyListOfFinancialOperation() {
        List<FinancialOperation> operations = List.of();
        given(repository.findByPeriod(any(LocalDate.class), any(LocalDate.class))).willReturn(operations);
        APIResponse response = findFinancialOperationByPeriod.findByPeriod(initialDate, finalDate);
        assertEquals(response.content(), null);
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getMessage(), "There's no financial Operation to be recovered.");
    }

    @Test
    public void shouldReturnResponseErrorWhenSomethingIsWrong() {
        Exception exception = new RuntimeException("Mocked Error");
        given(repository.findByPeriod(initialDate, finalDate)).willThrow(exception);
        APIResponse response = findFinancialOperationByPeriod.findByPeriod(initialDate, finalDate);
        assertEquals(response.getStatus(), 400);
        assertEquals(response.getMessage(), "Something went wrong. Consult errors.");
        String errorAsString = "[Message: {message:'Mocked Error', stacktrace:'Mocked.Error'}]";
        assertEquals(response.content().toString(), errorAsString);
    }
}