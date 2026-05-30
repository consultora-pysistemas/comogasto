package py.com.pysistemas.common.events.exception;

/*
 * @author josec
 * @project comogastoapp
 */
public class EventPublishingException extends RuntimeException{
    public EventPublishingException(String message, Throwable cause) {
        super(message, cause);
    }
}
