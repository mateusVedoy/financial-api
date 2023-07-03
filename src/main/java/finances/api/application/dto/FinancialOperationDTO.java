package finances.api.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class FinancialOperationDTO {
    private Long id;
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

    public FinancialOperationDTO(Long id, String type, double amount, LocalDate date, LocalTime hour) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.hour = hour;
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHour() {
        return hour;
    }

    @Override
    public String toString() {
        return "FinancialOperationDTO: {" +
                "id:" + id +
                ", type:'" + type + '\'' +
                ", amount:" + amount +
                ", date:" + date +
                ", hour:" + hour +
                '}';
    }
}
