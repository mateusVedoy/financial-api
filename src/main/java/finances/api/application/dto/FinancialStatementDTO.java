package finances.api.application.dto;

import java.io.Serializable;
import java.util.List;

public class FinancialStatementDTO implements Serializable {
    private String state;
    private double balance;
    private FinancialStatementPeriodDTO period;
    private List<FinancialOperationDTO> financialOperations;

    public FinancialStatementDTO(String state, double balance, String initialDate, String finalDate) {
        this.state = state;
        this.balance = balance;
        this.period = setPeriod(initialDate, finalDate);
    }

    private FinancialStatementPeriodDTO setPeriod(String init, String fin) {
        return new FinancialStatementPeriodDTO(init, fin);
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

    public FinancialStatementPeriodDTO getPeriod() {
        return period;
    }

    public List<FinancialOperationDTO> getFinancialOperations() {
        return financialOperations;
    }
}
