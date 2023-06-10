package finances.api.application.dto;

public enum OperationTypeDTO {
    IN ("input"),
    OUT ("output");

    private String value;
    OperationTypeDTO(String value) {
        value = value;
    }

    public String getValue() {
        return value;
    }
}
