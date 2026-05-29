package py.com.pysistemas.common.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import py.com.pysistemas.common.events.envelope.EventEnvelopeFactory;
import py.com.pysistemas.common.events.publisher.EventPublisher;
import py.com.pysistemas.common.events.publisher.KafkaEventPublisher;

/*
 * @author josec
 * @project comogastoapp
 */
public class CommonEventsConfiguration {
    @Bean
    public EventPublisher eventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        return new KafkaEventPublisher(kafkaTemplate);
    }

    @Bean
    public EventEnvelopeFactory eventEnvelopeFactory(
            @Value("${spring.application.name:unknown-service}") String sourceService
    ) {
        return new EventEnvelopeFactory(sourceService);
    }
}
