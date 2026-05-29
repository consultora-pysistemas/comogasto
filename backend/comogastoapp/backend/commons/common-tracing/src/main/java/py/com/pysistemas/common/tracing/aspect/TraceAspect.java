package py.com.pysistemas.common.tracing.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import py.com.pysistemas.common.tracing.annotation.Traced;
import py.com.pysistemas.common.tracing.context.TraceContext;
import py.com.pysistemas.common.tracing.event.TraceProcessPayload;
import py.com.pysistemas.common.tracing.event.TraceStepPayload;
import py.com.pysistemas.common.tracing.model.TraceStatus;
import py.com.pysistemas.common.tracing.publisher.TraceEventPublisher;
import py.com.pysistemas.common.tracing.util.TraceNameResolver;
import py.com.pysistemas.commonsecurity.context.TenantContext;
import py.com.pysistemas.commonsecurity.context.UserContext;
import py.com.pysistemas.commonweb.request.CorrelationIdContext;

/*
 * @author josec
 * @project comogastoapp
 */
public class TraceAspect {
    private final TraceEventPublisher traceEventPublisher;
    private final String sourceService;

    public TraceAspect(
            TraceEventPublisher traceEventPublisher,
            String sourceService
    ) {
        this.traceEventPublisher = traceEventPublisher;
        this.sourceService = sourceService;
    }

    @Around("@annotation(traced)")
    public Object trace(
            ProceedingJoinPoint joinPoint,
            Traced traced
    ) throws Throwable {

        String traceId = TraceContext.getOrCreateTraceId();
        UUID processId = TraceContext.getOrCreateProcessId();
        UUID parentStepId = TraceContext.getCurrentStepId();
        UUID stepId = UUID.randomUUID();

        String stepName = TraceNameResolver.resolve(joinPoint, traced.name());

        Instant startedAt = Instant.now();
        long start = System.currentTimeMillis();

        if (traced.startProcess()) {
            publishProcessStarted(processId, traceId, stepName, startedAt);
        }

        TraceContext.setCurrentStepId(stepId);

        publishStepStarted(
                stepId,
                processId,
                parentStepId,
                traceId,
                stepName,
                traced,
                startedAt
        );

        try {
            Object result = joinPoint.proceed();

            long durationMs = System.currentTimeMillis() - start;
            Instant finishedAt = Instant.now();

            publishStepCompleted(
                    stepId,
                    processId,
                    parentStepId,
                    traceId,
                    stepName,
                    traced,
                    startedAt,
                    finishedAt,
                    durationMs
            );

            if (traced.finishProcess()) {
                publishProcessCompleted(
                        processId,
                        traceId,
                        stepName,
                        startedAt,
                        finishedAt,
                        durationMs
                );
            }

            return result;

        } catch (Throwable throwable) {
            long durationMs = System.currentTimeMillis() - start;
            Instant finishedAt = Instant.now();

            publishStepFailed(
                    stepId,
                    processId,
                    parentStepId,
                    traceId,
                    stepName,
                    traced,
                    startedAt,
                    finishedAt,
                    durationMs,
                    throwable
            );

            publishProcessFailed(
                    processId,
                    traceId,
                    stepName,
                    startedAt,
                    finishedAt,
                    durationMs,
                    throwable
            );

            throw throwable;

        } finally {
            TraceContext.setCurrentStepId(parentStepId);
        }
    }

    private void publishProcessStarted(
            UUID processId,
            String traceId,
            String name,
            Instant startedAt
    ) {
        TraceProcessPayload payload = new TraceProcessPayload(
                processId,
                traceId,
                CorrelationIdContext.get(),
                name,
                sourceService,
                TenantContext.getTenantId(),
                TenantContext.getCompanyId(),
                TenantContext.getBranchId(),
                UserContext.getUserId(),
                UserContext.getUsername(),
                TraceStatus.STARTED,
                startedAt,
                null,
                null,
                null,
                Map.of()
        );

        traceEventPublisher.publishProcessStarted(payload);
    }

    private void publishProcessCompleted(
            UUID processId,
            String traceId,
            String name,
            Instant startedAt,
            Instant finishedAt,
            Long durationMs
    ) {
        TraceProcessPayload payload = new TraceProcessPayload(
                processId,
                traceId,
                CorrelationIdContext.get(),
                name,
                sourceService,
                TenantContext.getTenantId(),
                TenantContext.getCompanyId(),
                TenantContext.getBranchId(),
                UserContext.getUserId(),
                UserContext.getUsername(),
                TraceStatus.COMPLETED,
                startedAt,
                finishedAt,
                durationMs,
                null,
                Map.of()
        );

        traceEventPublisher.publishProcessCompleted(payload);
    }

    private void publishProcessFailed(
            UUID processId,
            String traceId,
            String name,
            Instant startedAt,
            Instant finishedAt,
            Long durationMs,
            Throwable throwable
    ) {
        TraceProcessPayload payload = new TraceProcessPayload(
                processId,
                traceId,
                CorrelationIdContext.get(),
                name,
                sourceService,
                TenantContext.getTenantId(),
                TenantContext.getCompanyId(),
                TenantContext.getBranchId(),
                UserContext.getUserId(),
                UserContext.getUsername(),
                TraceStatus.FAILED,
                startedAt,
                finishedAt,
                durationMs,
                throwable.getMessage(),
                Map.of("errorClass", throwable.getClass().getName())
        );

        traceEventPublisher.publishProcessFailed(payload);
    }

    private void publishStepStarted(
            UUID stepId,
            UUID processId,
            UUID parentStepId,
            String traceId,
            String stepName,
            Traced traced,
            Instant startedAt
    ) {
        TraceStepPayload payload = new TraceStepPayload(
                stepId,
                processId,
                parentStepId,
                traceId,
                CorrelationIdContext.get(),
                stepName,
                traced.type(),
                sourceService,
                TraceStatus.STARTED,
                startedAt,
                null,
                null,
                null,
                null,
                Map.of()
        );

        traceEventPublisher.publishStepStarted(payload);
    }

    private void publishStepCompleted(
            UUID stepId,
            UUID processId,
            UUID parentStepId,
            String traceId,
            String stepName,
            Traced traced,
            Instant startedAt,
            Instant finishedAt,
            Long durationMs
    ) {
        TraceStepPayload payload = new TraceStepPayload(
                stepId,
                processId,
                parentStepId,
                traceId,
                CorrelationIdContext.get(),
                stepName,
                traced.type(),
                sourceService,
                TraceStatus.COMPLETED,
                startedAt,
                finishedAt,
                durationMs,
                null,
                null,
                Map.of()
        );

        traceEventPublisher.publishStepCompleted(payload);
    }

    private void publishStepFailed(
            UUID stepId,
            UUID processId,
            UUID parentStepId,
            String traceId,
            String stepName,
            Traced traced,
            Instant startedAt,
            Instant finishedAt,
            Long durationMs,
            Throwable throwable
    ) {
        TraceStepPayload payload = new TraceStepPayload(
                stepId,
                processId,
                parentStepId,
                traceId,
                CorrelationIdContext.get(),
                stepName,
                traced.type(),
                sourceService,
                TraceStatus.FAILED,
                startedAt,
                finishedAt,
                durationMs,
                throwable.getClass().getName(),
                throwable.getMessage(),
                Map.of()
        );

        traceEventPublisher.publishStepFailed(payload);
    }
}
