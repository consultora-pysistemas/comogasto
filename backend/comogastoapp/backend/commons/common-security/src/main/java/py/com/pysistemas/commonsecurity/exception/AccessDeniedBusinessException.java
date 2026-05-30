package py.com.pysistemas.commonsecurity.exception;

/*
 * @author josec
 * @project comogastoapp
 */
public class AccessDeniedBusinessException extends RuntimeException{
    public AccessDeniedBusinessException(String message) {
        super(message);
    }
}
