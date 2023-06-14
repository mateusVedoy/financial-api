package finances.api.application.dto;

public enum OperationTypeDTO {
    IN ("input"),
    OUT ("output");

    private final String value;
    OperationTypeDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
