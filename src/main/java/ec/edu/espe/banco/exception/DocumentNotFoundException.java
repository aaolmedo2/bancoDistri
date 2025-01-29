package ec.edu.espe.banco.exception;

public class DocumentNotFoundException extends Exception {

    private String entityName;

    public DocumentNotFoundException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
