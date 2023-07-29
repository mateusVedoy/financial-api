package finances.api.application.dto;

import java.io.Serializable;
import java.util.List;

public class FinancialStatementDTO implements Serializable {
    private String state;
    private double balance;
    private FinancialStatementPeriodDTO period;
    private FinancialStatementTotalDTO total;

    public FinancialStatementDTO(
            String state,
            double balance,
            String initialDate,
            String finalDate,
            FinancialStatementTotalDTO total) {
        this.state = state;
        this.balance = balance;
        this.period = setPeriod(initialDate, finalDate);
        this.total = total;
    }
    public void setTotal(FinancialStatementTotalDTO total) {
        this.total = total;
    }
    private FinancialStatementPeriodDTO setPeriod(String init, String fin) {
        return new FinancialStatementPeriodDTO(init, fin);
    }
    public FinancialStatementTotalDTO getTotal() {
        return total;
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
}
