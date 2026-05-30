package py.com.pysistemas.common.events.consumer;

import py.com.pysistemas.common.events.envelope.EventEnvelope;

/*
 * @author josec
 * @project comogastoapp
 */
public interface EventHandler<T> {
    void handle(EventEnvelope<T> event);
}
