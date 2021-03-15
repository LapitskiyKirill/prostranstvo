package coursework.prostranstvo.model.exception;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String exMessage) {
        super(exMessage);
    }
}