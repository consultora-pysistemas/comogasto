package py.com.pysistemas.common.audit.publisher;

import java.util.Map;

import py.com.pysistemas.common.audit.event.AuditEventPayload;
import py.com.pysistemas.common.events.envelope.EventEnvelope;
import py.com.pysistemas.common.events.envelope.EventEnvelopeFactory;
import py.com.pysistemas.common.events.publisher.EventPublisher;
import py.com.pysistemas.common.events.topic.PlatformTopics;

/*
 * @author josec
 * @project comogastoapp
 */
public class AuditEventPublisher {
    private final EventPublisher eventPublisher;
    private final EventEnvelopeFactory eventEnvelopeFactory;

    public AuditEventPublisher(
            EventPublisher eventPublisher,
            EventEnvelopeFactory eventEnvelopeFactory
    ) {
        this.eventPublisher = eventPublisher;
        this.eventEnvelopeFactory = eventEnvelopeFactory;
    }

    public void publish(AuditEventPayload payload) {
        EventEnvelope<AuditEventPayload> event = eventEnvelopeFactory.create(
                PlatformTopics.AUDIT_EVENT_CREATED_V1,
                "1.0",
                payload,
                payload.traceId(),
                "audit-" + payload.auditId(),
                Map.of("module", payload.module())
        );

        eventPublisher.publish(PlatformTopics.AUDIT_EVENT_CREATED_V1, event);
    }
}
