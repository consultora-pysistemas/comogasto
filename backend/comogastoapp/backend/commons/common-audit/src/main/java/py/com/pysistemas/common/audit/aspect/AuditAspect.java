package py.com.pysistemas.common.audit.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import py.com.pysistemas.common.audit.annotation.Auditable;
import py.com.pysistemas.common.audit.event.AuditEventPayload;
import py.com.pysistemas.common.audit.masking.SensitiveDataMasker;
import py.com.pysistemas.common.audit.model.AuditResult;
import py.com.pysistemas.common.audit.publisher.AuditEventPublisher;
import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.context.UserContext;
import py.com.pysistemas.commonweb.request.CorrelationIdContext;

/*
 * @author josec
 * @project comogastoapp
 */
public class AuditAspect {
    private final AuditEventPublisher auditEventPublisher;
    private final SensitiveDataMasker sensitiveDataMasker;
    private final String sourceService;

    public AuditAspect(
            AuditEventPublisher auditEventPublisher,
            SensitiveDataMasker sensitiveDataMasker,
            String sourceService
    ) {
        this.auditEventPublisher = auditEventPublisher;
        this.sensitiveDataMasker = sensitiveDataMasker;
        this.sourceService = sourceService;
    }

    @Around("@annotation(auditable)")
    public Object audit(
            ProceedingJoinPoint joinPoint,
            Auditable auditable
    ) throws Throwable {

        long start = System.currentTimeMillis();
        UUID auditId = UUID.randomUUID();

        Object request = null;
        Object response = null;

        try {
            if (auditable.includeRequest()) {
                request = maskArguments(joinPoint.getArgs());
            }

            Object result = joinPoint.proceed();

            if (auditable.includeResponse()) {
                response = sensitiveDataMasker.mask(result);
            }

            publish(
                    auditId,
                    auditable,
                    AuditResult.SUCCESS,
                    request,
                    response,
                    null,
                    null,
                    System.currentTimeMillis() - start
            );

            return result;

        } catch (Throwable throwable) {

            publish(
                    auditId,
                    auditable,
                    AuditResult.FAILED,
                    request,
                    null,
                    throwable.getClass().getName(),
                    throwable.getMessage(),
                    System.currentTimeMillis() - start
            );

            throw throwable;
        }
    }

    private Object maskArguments(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }

        Object[] masked = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            masked[i] = sensitiveDataMasker.mask(args[i]);
        }

        return masked;
    }

    private void publish(
            UUID auditId,
            Auditable auditable,
            AuditResult result,
            Object request,
            Object response,
            String exceptionClass,
            String exceptionMessage,
            long durationMs
    ) {
        AuditEventPayload payload = new AuditEventPayload(
                auditId,
                auditable.type(),
                result,
                auditable.module(),
                auditable.action(),
                auditable.entity(),
                null,
                UserContext.getUsername(),
                UserContext.getUserId(),
                TenantContext.getTenantId(),
                TenantContext.getCompanyId(),
                TenantContext.getBranchId(),
                CorrelationIdContext.get(),
                CorrelationIdContext.get(),
                sourceService,
                Instant.now(),
                durationMs,
                request,
                response,
                exceptionClass,
                exceptionMessage,
                Map.of()
        );

        auditEventPublisher.publish(payload);
    }
}
