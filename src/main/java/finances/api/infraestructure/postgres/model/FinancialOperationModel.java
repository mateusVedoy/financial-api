package finances.api.infraestructure.postgres.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financialoperation")
public class FinancialOperationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fop_code")
    private Long id;

    @Column(name = "ope_code")
    private Long type;

    @Column(name = "fop_amount")
    private double amount;

    @Column(name = "fop_executedAt")
    private LocalDateTime executedAt;

   public FinancialOperationModel(){}
    public FinancialOperationModel(String type, double amount, LocalDateTime executedAt){
        this.type = setType(type);
        this.amount = amount;
        this.executedAt = executedAt;
    }

    public FinancialOperationModel(Long id, Long type, double amount, LocalDateTime executedAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.executedAt = executedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    private Long setType(String type) {
        if(type.equals("Entrance"))
            return 1L;
        else if(type.equals("Exit"))
            return 2L;
        return null;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}
