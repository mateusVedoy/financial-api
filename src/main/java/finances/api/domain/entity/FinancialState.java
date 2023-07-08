package finances.api.domain.entity;

public enum FinancialState {
    PROFIT("profit"),
    LOSS("loss");

    private final String value;
    FinancialState(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
