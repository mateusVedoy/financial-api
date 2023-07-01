package finances.api.infraestructure.repository;

import finances.api.application.converter.FinancialOperationModelConverter;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessException;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.postgres.IFinancialOperationRepository;
import finances.api.infraestructure.postgres.model.FinancialOperationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class FinancialOperationRepositoryTest {

    @InjectMocks
    FinancialOperationRepository repository = new FinancialOperationRepository();
    @Mock
    IFinancialOperationRepository repo;
    @Mock
    FinancialOperationModelConverter converter = new FinancialOperationModelConverter();

    Date date = new Date();
    LocalDateTime ts = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withNano(0);
    FinancialOperation entity = new FinancialOperation("input", 100, ts);

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldCallJPARepositoryInterfaceOnceWhenSave() {
        repository.save(entity);
        verify(repo, times(1)).save(any(FinancialOperationModel.class));
    }

    @Test
    public void shouldCallJPARepositoryInterfaceOnceWhenFindAll() {
        repository.findAll();
        verify(repo, times(1)).findAll();
    }

    @Test
    public void shouldCallConverterSameTimesThanModelsInList() throws BusinessValidationError {
        given(repo.findAll()).willReturn(new ArrayList<>(List.of(
                new FinancialOperationModel(1L, 1L, 10, ts)
        )));
        repository.findAll();
        verify(converter, times(1)).convert(any(FinancialOperationModel.class));
    }

    @Test
    public void shouldCallCallSaveFromJPARepositoryInterfaceWithData() {
        repository.save(entity);
        ArgumentCaptor<FinancialOperationModel> argument = ArgumentCaptor.forClass(FinancialOperationModel.class);
        verify(repo).save(argument.capture());
    }

    @Test
    public void shouldReturnsDataFromJPARepositoryWhenCallsFindAllMethod() throws BusinessValidationError {
        LocalDateTime ts = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withNano(0);
        FinancialOperationModel model1 = new FinancialOperationModel(1L, 2L, 1200, ts);
        List<FinancialOperationModel> data = new ArrayList<>(List.of(
                model1
        ));
        FinancialOperation op1 = new FinancialOperation(1L, 2L, 1200, ts);
        given(repo.findAll()).willReturn(data);
        given(converter.convert(model1)).willReturn(op1);
        assertEquals(repository.findAll(), new ArrayList<>(List.of(op1)));
    }

    @Test
    public void shouldThrownBusinessExceptionWhenTryToConvertModelToEntity() throws BusinessValidationError {
        FinancialOperationModel model1 = new FinancialOperationModel(1L, 2L, 1200, null);
        List<FinancialOperationModel> data = new ArrayList<>(List.of(
                model1
        ));
        given(repo.findAll()).willReturn(data);
        given(converter.convert(model1)).willThrow(new BusinessValidationError(List.of()));
        assertThrows(BusinessException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                repository.findAll();
            }
        });
    }
    @Test
    public void shouldReturnAFinancialOperationInstanceWhenSearchWithAValidId() throws BusinessValidationError {
        FinancialOperationModel model1 = new FinancialOperationModel(1L, 2L, 1200, null);
        FinancialOperation op1 = new FinancialOperation(1L, 2L, 1200, ts);
        given(repo.findById(anyLong())).willReturn(Optional.of(model1));
        given(converter.convert(model1)).willReturn(op1);
        FinancialOperation operation = repository.findById(1L);
        assertEquals(operation, op1);
    }
    @Test
    public void shouldThrownNoSuchElementExceptionWhenSearchWithAnInvalidId() throws BusinessValidationError {
        FinancialOperationModel model1 = new FinancialOperationModel(1L, 2L, 1200, null);
        FinancialOperation op1 = new FinancialOperation(1L, 2L, 1200, ts);
        given(converter.convert(model1)).willReturn(op1);
        assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                repository.findById(anyLong());
            }
        });
    }
}