package coursework.prostranstvo.Model.Exception;

public class ApiException extends RuntimeException{
    public ApiException(String exMessage){
        super(exMessage);
    }
}