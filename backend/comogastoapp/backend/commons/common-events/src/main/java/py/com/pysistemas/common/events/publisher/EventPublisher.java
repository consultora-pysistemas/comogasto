package py.com.pysistemas.common.events.publisher;

import py.com.pysistemas.common.events.envelope.EventEnvelope;

/*
 * @author josec
 * @project comogastoapp
 */
public interface EventPublisher {
    <T> void publish(String topic, EventEnvelope<T> event);
}
