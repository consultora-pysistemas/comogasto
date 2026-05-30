package py.com.pysistemas.common.events.envelope;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.context.UserContext;
import py.com.pysistemas.commonweb.request.CorrelationIdContext;

/*
 * @author josec
 * @project comogastoapp
 */
public class EventEnvelopeFactory {
    private final String sourceService;

    public EventEnvelopeFactory(String sourceService) {
        this.sourceService = sourceService;
    }

    public <T> EventEnvelope<T> create(
            String eventType,
            String eventVersion,
            T payload,
            String traceId,
            String idempotencyKey,
            Map<String, Object> metadata
    ) {
        return new EventEnvelope<>(
                UUID.randomUUID(),
                eventType,
                eventVersion,
                Instant.now(),
                TenantContext.getTenantId(),
                TenantContext.getCompanyId(),
                TenantContext.getBranchId(),
                UserContext.getUserId(),
                UserContext.getUsername(),
                CorrelationIdContext.get(),
                traceId,
                sourceService,
                idempotencyKey,
                payload,
                metadata
        );
    }
}
