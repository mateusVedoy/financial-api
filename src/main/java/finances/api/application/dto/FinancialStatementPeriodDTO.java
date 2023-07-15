package finances.api.application.dto;

public class FinancialStatementPeriodDTO {
    private String initialDate;
    private String finalDate;

    public FinancialStatementPeriodDTO(String begin, String end) {
        this.initialDate = begin;
        this.finalDate = end;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }
}
