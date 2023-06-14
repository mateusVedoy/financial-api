package finances.api.application.controller;

import finances.api.application.dto.FinancialOperationDTO;
import finances.api.application.response.APIResponse;
import finances.api.application.useCase.CreateFinancialOperation;
import finances.api.application.useCase.FindAllFinancialOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finances/operation")
public class FinancialOperationController {

    @Autowired
    CreateFinancialOperation createFinancialOperation;
    @Autowired
    FindAllFinancialOperation findAllFinancialOperation;

    @PostMapping(value = "/save")
    public ResponseEntity save(@RequestBody FinancialOperationDTO dto) {
        APIResponse response = createFinancialOperation.save(dto);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity findAll() {
        APIResponse response = findAllFinancialOperation.findAll();
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }
}
