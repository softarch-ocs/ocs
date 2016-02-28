package services.exceptions;

public class OcsServiceException extends RuntimeException {

    public OcsServiceException() {
    }

    public OcsServiceException(String message) {
        super(message);
    }

    public OcsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public OcsServiceException(Throwable cause) {
        super(cause);
    }

    public OcsServiceException(String message, Throwable cause, 
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
