package finances.api.domain.entity;

public enum OperationType {
    IN ("Entrance"),
    OUT ("Exit");

    private final String value;
    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
