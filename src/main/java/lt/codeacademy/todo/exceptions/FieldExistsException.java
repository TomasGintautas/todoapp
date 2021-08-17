package lt.codeacademy.todo.exceptions;

public class FieldExistsException extends RuntimeException{

    private final String field;

    public FieldExistsException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
