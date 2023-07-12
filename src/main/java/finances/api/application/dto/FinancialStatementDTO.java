package finances.api.application.dto;

import java.util.List;

public class FinancialStatementDTO {
    private String state;
    private double balance;
    private String period;
    private List<FinancialOperationDTO> financialOperations;

    public FinancialStatementDTO(String state, double balance, String initialDate, String finalDate) {
        this.state = state;
        this.balance = balance;
        this.period = setPeriod(initialDate, finalDate);
    }

    private String setPeriod(String init, String fin) {
        return init.concat(" - ").concat(fin);
    }
    public void setFinancialOperations(List<FinancialOperationDTO> financialOperations) {
        this.financialOperations = financialOperations;
    }

    public String getState() {
        return state;
    }

    public double getBalance() {
        return balance;
    }

    public String getPeriod() {
        return period;
    }

    public List<FinancialOperationDTO> getFinancialOperations() {
        return financialOperations;
    }
}
