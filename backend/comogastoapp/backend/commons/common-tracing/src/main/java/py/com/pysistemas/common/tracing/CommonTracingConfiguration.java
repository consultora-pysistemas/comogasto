package py.com.pysistemas.common.tracing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import py.com.pysistemas.common.events.envelope.EventEnvelopeFactory;
import py.com.pysistemas.common.events.publisher.EventPublisher;
import py.com.pysistemas.common.tracing.aspect.TraceAspect;
import py.com.pysistemas.common.tracing.publisher.TraceEventPublisher;

/*
 * @author josec
 * @project comogastoapp
 */
public class CommonTracingConfiguration {
    @Bean
    public TraceEventPublisher traceEventPublisher(
            EventPublisher eventPublisher,
            EventEnvelopeFactory eventEnvelopeFactory
    ) {
        return new TraceEventPublisher(eventPublisher, eventEnvelopeFactory);
    }

    @Bean
    public TraceAspect traceAspect(
            TraceEventPublisher traceEventPublisher,
            @Value("${spring.application.name:unknown-service}") String sourceService
    ) {
        return new TraceAspect(traceEventPublisher, sourceService);
    }
}
