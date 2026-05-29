package py.com.pysistemas.common.tracing.event;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import py.com.pysistemas.common.tracing.model.TraceStatus;
import py.com.pysistemas.common.tracing.model.TraceStepType;

/*
 * @author josec
 * @project comogastoapp
 */
public record TraceStepPayload(
        UUID stepId,
        UUID processId,
        UUID parentStepId,
        String traceId,
        String correlationId,
        String stepName,
        TraceStepType stepType,
        String sourceService,
        TraceStatus status,
        Instant startedAt,
        Instant finishedAt,
        Long durationMs,
        String errorClass,
        String errorMessage,
        Map<String, Object> metadata
) {
}
