package finances.api.application.useCase;

import finances.api.application.converter.FinancialOperationConverter;
import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.domain.entity.FinancialOperation;
import finances.api.domain.exception.BusinessValidationError;
import finances.api.infraestructure.repository.FinancialOperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class FindFinancialOperationByIdTest {

    @InjectMocks
    private FindFinancialOperationById findFinancialOperationById;
    @Mock
    private FinancialOperationRepository repository;
    @Mock
    private FinancialOperationConverter converter;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    LocalDate date = LocalDate.parse("2023-07-09");
    LocalTime time = LocalTime.parse("11:09:11");
    String str = date.toString().concat(" ").concat(time.toString());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime ts = LocalDateTime.parse(str, formatter);

    @Test
    public void shouldReturnResponseSuccessWhenThereIsDataToReturn() throws BusinessValidationError {
        FinancialOperation operation = new FinancialOperation(1L, 1L, 100, ts);
        FinancialOperationDTO dto = new FinancialOperationDTO(1L, "Input", 100, date, time);
        given(repository.findById(anyLong())).willReturn(operation);
        given(converter.convert(operation)).willReturn(dto);
        assertEquals(findFinancialOperationById.findById(1L).getStatus(), 200);
        assertEquals(findFinancialOperationById.findById(1L).getMessage(), "Financial operation fetched bellow");
        assertInstanceOf(ResponseSuccess.class, findFinancialOperationById.findById(1L));
    }

    @Test
    public void shouldReturnResponseSuccessEvenWhenCatchNoSuchElementException() {
        given(repository.findById(anyLong())).willThrow(new NoSuchElementException());
        assertInstanceOf(ResponseSuccess.class, findFinancialOperationById.findById(anyLong()));
        assertEquals(findFinancialOperationById.findById(anyLong()).getMessage(), "There's no data to be fetched");
        assertEquals(findFinancialOperationById.findById(anyLong()).getStatus(), 200);
    }
}