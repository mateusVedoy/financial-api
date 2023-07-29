package finances.api.application.response;

public enum StatusMessage {
    EMPTY_SUCCESS("There's no financial Operation to be recovered."),
    SUCCESS("Financial Operation fetched bellow."),
    CREATED("Financial Operation saved successfully."),
    ERROR("Something went wrong. Consult errors.");


    private final String value;
    StatusMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
