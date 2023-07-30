package finances.api.application.controller;


import finances.api.application.response.APIResponse;
import finances.api.application.useCase.FindFinancialOperationByPeriod;
import finances.api.application.useCase.ProduceFinancialStatementByPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/finances/statement")
public class FinancialStatementController {

    @Autowired
    private ProduceFinancialStatementByPeriod financialStatementByPeriod;
    @GetMapping("/balance")
    public ResponseEntity getBalancePerPeriod(@RequestParam("initialDate") String initialDate, @RequestParam("finalDate") String finalDate) {
        APIResponse response = financialStatementByPeriod.produce(initialDate, finalDate);
        return new ResponseEntity<APIResponse>(response, HttpStatus.resolve(response.getStatus()));
    }
}