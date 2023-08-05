package finances.api.domain.entity;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinancialStatement extends Entity {

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    private String state;
    private double balance;
    private LocalDate startDate;
    private LocalDate finalDate;
    private double totalInputAmount;
    private double totalOutputAmount;

    public FinancialStatement(){
        super();
    }

    public FinancialStatement(
            double balance,
            String startDate,
            String finalDate
    ){
        super();
        this.balance = balance;
        this.startDate = parseStringDateToLocalDate(startDate);
        this.finalDate = parseStringDateToLocalDate(finalDate);
        setType();
        validate();
    }

    public FinancialStatement(String startDate, String finalDate){
        super();
        this.startDate = parseStringDateToLocalDate(startDate);
        this.finalDate = parseStringDateToLocalDate(finalDate);
        validate();
    }
    public void setBalance(double balance) {
        this.balance = balance;
        setType();
    }
    @Override
    public void validate() {
        if(startDate == null || finalDate == null)
            addError("Financial statement dates cannot be null", "financialStatement.date");

        if(startDate != null && finalDate != null)
            if(startDate.isAfter(finalDate))
                addError("Final date cannot be less than initial date", "financialStatement.dates");
    }
    private boolean wasDatesToPeriodInformedCorrectly(String date) {
        Pattern pattern = Pattern.compile(DATE_REGEX);
        Matcher dateMatcher = pattern.matcher(date);
        return dateMatcher.matches();
    }

    public double getTotalInputAmount() {
        return totalInputAmount;
    }

    public double getTotalOutputAmount() {
        return totalOutputAmount;
    }
    public void setTotalInputAmount(double totalInputAmount) {
        this.totalInputAmount = totalInputAmount;
    }

    public void setTotalOutputAmount(double totalOutputAmount) {
        this.totalOutputAmount = totalOutputAmount;
    }

    private LocalDate parseStringDateToLocalDate(String dt) {
        if(wasDatesToPeriodInformedCorrectly(dt))
            return LocalDate.parse(dt);
        else
            addError(
                    "Cannot cast wrong dates format. Required pattern YYYY-MM-DD",
                    "financialStatement.dates"
            );
        return null;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }
}
