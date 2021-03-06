package lt.codeacademy.todo.exceptions;

public class FieldNotFoundException extends RuntimeException{

    private final String field;

    public FieldNotFoundException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
