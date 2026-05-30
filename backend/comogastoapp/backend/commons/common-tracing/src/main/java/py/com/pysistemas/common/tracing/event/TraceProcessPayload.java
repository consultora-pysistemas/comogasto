package py.com.pysistemas.common.tracing.event;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import py.com.pysistemas.common.tracing.model.TraceStatus;

/*
 * @author josec
 * @project comogastoapp
 */
public record TraceProcessPayload(
        UUID processId,
        String traceId,
        String correlationId,
        String name,
        String sourceService,
        UUID tenantId,
        UUID companyId,
        UUID administrationId,
        UUID userId,
        String username,
        TraceStatus status,
        Instant startedAt,
        Instant finishedAt,
        Long durationMs,
        String errorMessage,
        Map<String, Object> metadata
) {
}
