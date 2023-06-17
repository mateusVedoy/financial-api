package finances.api.domain.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FinancialOperation extends Entity {

    private Long id;
    private String type;
    private double amount;
    private LocalDateTime executedAt;

    public FinancialOperation(){}

    //for explicit date
    public FinancialOperation(
            String type,
            double amount,
            LocalDateTime date
    ){
        this.type = setType(type);
        this.amount = amount;
        this.executedAt = date;
        this.validate();
    }

    //for implicit date
    public FinancialOperation(
            String type,
            double amount
    ){
        this.type = setType(type);
        this.amount = amount;
        this.executedAt = setImplicitDateTime();
        this.validate();
    }

    public FinancialOperation(Long id, Long type, double amount, LocalDateTime executedAt) {
        this.id = id;
        this.type = setType(type);
        this.amount = amount;
        this.executedAt = executedAt;
        this.validate();
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    @Override
    protected void validate() {
        if(this.isOperationTypeInvalid())
            super.addError("Operation type cannot be empty or null.", "domain.FinancialOperation.type");
        if(!this.isAmountAValidValue())
            super.addError("Operation amount value cannot be zero or less.", "domain.FinancialOperation.amount");
        if(this.isExecutedAtInvalid())
            super.addError("Operation date and hour cannot be empty.", "domain.FinancialOperation.executedAt");
    }

    private boolean isOperationTypeInvalid() {
        return this.type == null || this.type.isBlank() || this.type.isEmpty();
    }

    private boolean isAmountAValidValue() {
        return this.amount > 0;
    }

    private boolean isExecutedAtInvalid() {
        return this.executedAt == null || this.executedAt.toString().isBlank() || this.executedAt.toString().isEmpty();
    }

    private String setType(String type) {
        if(type.equals("input"))
            return OperationType.IN.getValue();
        else if(type.equals("output"))
            return OperationType.OUT.getValue();
        else
            return null;
    }

    private String setType(Long type) {
        if(type == 1L)
            return OperationType.IN.getValue();
        else if(type == 2L)
            return OperationType.OUT.getValue();
        else
            return null;
    }

    private LocalDateTime setImplicitDateTime() {
        Date date = new Date();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withNano(0);
    }
}
