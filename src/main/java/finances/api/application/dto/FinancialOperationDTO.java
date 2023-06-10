package finances.api.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class FinancialOperationDTO {
    private String type;
    private double amount;
    private LocalDate date;
    private LocalTime hour;


    public FinancialOperationDTO(){}

    public FinancialOperationDTO(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public FinancialOperationDTO(String type, double amount, LocalDate date, LocalTime hour) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.hour = hour;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHour() {
        return hour;
    }
}
