package py.com.pysistemas.common.events.exception;

/*
 * @author josec
 * @project comogastoapp
 */
public class EventConsumptionException extends RuntimeException{
    public EventConsumptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
