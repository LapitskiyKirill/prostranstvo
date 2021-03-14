package coursework.prostranstvo.Model.Exception;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String exMessage) {
        super(exMessage);
    }
}