package ec.edu.espe.banco.exception;

public class DeleteException extends Exception {

    private String entityName;

    public DeleteException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
