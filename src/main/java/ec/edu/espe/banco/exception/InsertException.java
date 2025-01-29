package ec.edu.espe.banco.exception;

public class InsertException extends Exception {

    private String entityName;

    public InsertException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
