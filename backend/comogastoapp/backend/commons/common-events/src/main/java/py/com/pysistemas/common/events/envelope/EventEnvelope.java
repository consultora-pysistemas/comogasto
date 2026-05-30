package py.com.pysistemas.common.events.envelope;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/*
 * @author josec
 * @project comogastoapp
 */
public record EventEnvelope<T>(
        UUID eventId,
        String eventType,
        String eventVersion,
        Instant occurredAt,
        UUID tenantId,
        UUID companyId,
        UUID branchId,
        UUID userId,
        String username,
        String correlationId,
        String traceId,
        String sourceService,
        String idempotencyKey,
        T payload,
        Map<String, Object> metadata
) {
}
