package services.exceptions;

public class OcsPersistenceException extends OcsServiceException {

    public OcsPersistenceException() {
    }

    public OcsPersistenceException(String message) {
        super(message);
    }

    public OcsPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public OcsPersistenceException(Throwable cause) {
        super(cause);
    }

    public OcsPersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
