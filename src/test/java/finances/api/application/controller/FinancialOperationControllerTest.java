package finances.api.application.controller;

import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.ResponseError;
import finances.api.application.response.ResponseSuccess;
import finances.api.application.useCase.CreateFinancialOperation;
import finances.api.application.useCase.FindAllFinancialOperation;
import finances.api.application.useCase.FindFinancialOperationById;
import finances.api.domain.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

class FinancialOperationControllerTest {

    @InjectMocks
    private FinancialOperationController controller;
    @Mock
    private CreateFinancialOperation createFinancialOperation;
    @Mock
    private FindAllFinancialOperation findAllFinancialOperation;
    @Mock
    FindFinancialOperationById findFinancialOperationById;
    @Mock
    private FinancialOperationDTO dto;

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }


    @Test
    public void shouldResponseWithSuccessWhenCreateFinancialOperation() {
        ResponseSuccess response = new ResponseSuccess<>(201, "Financial Operation saved successfully.");
        given(createFinancialOperation.save(dto)).willReturn(response);
        ResponseEntity result = controller.save(dto);
        String responseSuccessToString = "ResponseSuccess{data:null, status:201, message:'Financial Operation saved successfully.'}";
        assertEquals(result.getBody().toString(), responseSuccessToString);
    }
    @Test
    public void shouldResponseWithSuccessAndDataWhenSearchingForFinancialOperation() {
        LocalDate date = LocalDate.parse("2023-07-09");
        LocalTime time = LocalTime.parse("11:09:11");
        FinancialOperationDTO dtoTwo = new FinancialOperationDTO(1L, "Input", 100, date, time);
        ResponseSuccess<FinancialOperationDTO> response = new ResponseSuccess<>(201, "Financial Operation saved successfully.", List.of(dtoTwo));
        given(findAllFinancialOperation.findAll()).willReturn(response);
        ResponseEntity result = controller.findAll();
        String responseToString = "ResponseSuccess: {data:[ FinancialOperationDTO: {id:1, type:'Input', amount:100.0, date:2023-07-09, hour:11:09:11} ], status:201, message:'Financial Operation saved successfully.'}";
        assertEquals(result.getBody().toString(), responseToString);
    }
    @Test
    public void shouldResponseWithOneErrorWhenSomethingWentWrong() {
        RuntimeException runtimeException = new RuntimeException("Mocked runtime exception");
        ResponseError error = new ResponseError(400, "Something went wrong. Consult errors.", runtimeException);
        given(findFinancialOperationById.findById(anyLong())).willReturn(error);
        ResponseEntity result = controller.findById(1L);
        String responseToString = "ResponseError{errors:[ Message: {message:'Mocked runtime exception', stacktrace:'Mocked.runtime.exception'} ], status:400, message:'Something went wrong. Consult errors.'}";
        assertEquals(result.getBody().toString(), responseToString);
    }
    @Test
    public void shouldResponseWithErrorListWhenSomethingWithMultipleErrorsWentWrong() {
        BusinessException exceptionOne = new BusinessException("Error One", "Error One");
        BusinessException exceptionTwo = new BusinessException("Error Two", "Error Two");
        BusinessException exceptionThree = new BusinessException("Error Three", "Error Three");
        ResponseError error = new ResponseError(400, "Something went wrong. Consult errors.", List.of(exceptionOne, exceptionTwo, exceptionThree));
        given(createFinancialOperation.save(dto)).willReturn(error);
        ResponseEntity result = controller.save(dto);
        String responseToString = "ResponseError{errors:[ Message: {message:'Error One', stacktrace:'Error One'} Message: {message:'Error Two', stacktrace:'Error Two'} Message: {message:'Error Three', stacktrace:'Error Three'} ], status:400, message:'Something went wrong. Consult errors.'}";
        assertEquals(result.getBody().toString(), responseToString);
    }
}