package finances.api.domain.entity;

import java.util.Date;
import java.util.List;

public class FinancialStatement {
    private String state;
    private double balance;
    private Date startDate;
    private Date finalDate;
    private List<FinancialOperation> operations;

    public FinancialStatement(){}

    public FinancialStatement(
            double balance,
            Date startDate,
            Date finalDate
    ){
        this.balance = balance;
        this.startDate = startDate;
        this.finalDate = finalDate;
        setType();
    }

    private void setType() {
        if(isBalancePositive())
            this.state = FinancialState.PROFIT.getValue();
        else
            this.state = FinancialState.LOSS.getValue();
    }

    private boolean isBalancePositive() {
        return this.balance > 0;
    }
    public String getState() {
        return state;
    }

    public double getBalance() {
        return balance;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public List<FinancialOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<FinancialOperation> operations) {
        this.operations = operations;
    }
}
