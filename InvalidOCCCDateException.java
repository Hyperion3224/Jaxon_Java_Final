public class InvalidOCCCDateException extends Exception {

    private static final String DEFAULT_MESSAGE = "Invalid OCCCDate";

    private String message;

    public InvalidOCCCDateException() {
        super();
        message = DEFAULT_MESSAGE;
    }

    public InvalidOCCCDateException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "InvalidOCCCDateException: " + message;
    }
}
