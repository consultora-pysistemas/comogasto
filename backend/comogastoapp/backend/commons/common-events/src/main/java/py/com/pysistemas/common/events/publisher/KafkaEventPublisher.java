package py.com.pysistemas.common.events.publisher;

import org.springframework.kafka.core.KafkaTemplate;

import py.com.pysistemas.common.events.envelope.EventEnvelope;
import py.com.pysistemas.common.events.exception.EventPublishingException;

/*
 * @author josec
 * @project comogastoapp
 */
public class KafkaEventPublisher implements EventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public <T> void publish(String topic, EventEnvelope<T> event) {
        try {
            String key = event.tenantId() != null
                    ? event.tenantId().toString()
                    : event.eventId().toString();

            kafkaTemplate.send(topic, key, event);
        } catch (Exception exception) {
            throw new EventPublishingException(
                    "Error publishing event to topic: " + topic,
                    exception
            );
        }
    }
}
