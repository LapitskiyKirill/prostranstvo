package coursework.prostranstvo.model.exception;

public class ApiException extends RuntimeException{
    public ApiException(String exMessage){
        super(exMessage);
    }
}