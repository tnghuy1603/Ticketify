package project.intro2se.ticketify.exception;

public class RefreshTokenException extends RuntimeException{
    public RefreshTokenException(String msg){
        super(msg);
    }
}
