package finances.api.application.controller;

import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.useCase.CreateFinancialOperation;
import finances.api.application.useCase.FindAllFinancialOperation;
import finances.api.application.useCase.FindFinancialOperationById;
import finances.api.application.useCase.FindFinancialOperationByPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/api/finances/operation")
public class FinancialOperationController {

    @Autowired
    CreateFinancialOperation createFinancialOperation;
    @Autowired
    FindAllFinancialOperation findAllFinancialOperation;
    @Autowired
    FindFinancialOperationById findFinancialOperationById;
    @Autowired
    FindFinancialOperationByPeriod findFinancialOperationByPeriod;

    @PostMapping(value = "/save")
    public ResponseEntity save(@RequestBody FinancialOperationDTO dto) {
        APIResponse response = createFinancialOperation.save(dto);
        return new ResponseEntity<APIResponse>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity findAll() {
        APIResponse response = findAllFinancialOperation.findAll();
        return new ResponseEntity<APIResponse>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(value = "/find/id/{financialOperationId}")
    public ResponseEntity findById(@PathVariable Long financialOperationId) {
        APIResponse response = findFinancialOperationById.findById(financialOperationId);
        return new ResponseEntity<APIResponse>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(value = "/find/period")
    public ResponseEntity findByPeriod(@RequestParam("initialDate") String initialDate, @RequestParam("finalDate") String finalDate) {
        LocalDate initial_date = LocalDate.parse(initialDate);
        LocalDate final_date = LocalDate.parse(finalDate);
        APIResponse response = findFinancialOperationByPeriod.findByPeriod(initial_date, final_date);
        return new ResponseEntity<APIResponse>(response, HttpStatus.resolve(response.getStatus()));
    }
}
