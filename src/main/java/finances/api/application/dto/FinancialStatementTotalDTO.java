package finances.api.application.dto;

import java.io.Serializable;

public class FinancialStatementTotalDTO implements Serializable {
    private double input;
    private double output;

    public FinancialStatementTotalDTO(double input, double output) {
        this.input = input;
        this.output = output;
    }

    public double getInput() {
        return input;
    }

    public double getOutput() {
        return output;
    }
}
