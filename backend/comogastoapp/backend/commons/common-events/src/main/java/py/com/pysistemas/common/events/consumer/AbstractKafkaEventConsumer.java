package py.com.pysistemas.common.events.consumer;

import py.com.pysistemas.common.events.envelope.EventEnvelope;
import py.com.pysistemas.common.events.exception.EventConsumptionException;

/*
 * @author josec
 * @project comogastoapp
 */
public abstract class AbstractKafkaEventConsumer<T> {
    protected void consume(
            EventEnvelope<T> event,
            EventHandler<T> handler
    ) {
        try {
            EventContextPropagation.apply(event);
            handler.handle(event);
        } catch (Exception exception) {
            throw new EventConsumptionException(
                    "Error consuming event: " + event.eventType(),
                    exception
            );
        } finally {
            EventContextPropagation.clear();
        }
    }
}
