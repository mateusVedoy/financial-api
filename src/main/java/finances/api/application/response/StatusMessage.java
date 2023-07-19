package finances.api.application.response;

public enum StatusMessage {
    EMPTY_SUCCESS("There's no financial operation to be fetched"),
    SUCCESS("Financial operation fetched bellow"),
    CREATED("Financial operation created successfully"),
    ERROR("Something went wrong. Consult errors.");


    private final String value;
    StatusMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
