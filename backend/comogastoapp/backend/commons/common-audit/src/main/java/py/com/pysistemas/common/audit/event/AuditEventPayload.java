package py.com.pysistemas.common.audit.event;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import py.com.pysistemas.common.audit.model.AuditResult;
import py.com.pysistemas.common.audit.model.AuditType;

/*
 * @author josec
 * @project comogastoapp
 */public record AuditEventPayload(
        UUID auditId,
        AuditType auditType,
        AuditResult result,
        String module,
        String action,
        String entity,
        String entityId,
        String username,
        UUID userId,
        UUID tenantId,
        UUID companyId,
        UUID administrationId,
        String correlationId,
        String traceId,
        String sourceService,
        Instant occurredAt,
        Long durationMs,
        Object request,
        Object response,
        String exceptionClass,
        String exceptionMessage,
        Map<String, Object> metadata
) {
}