package py.com.pysistemas.common.observability.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/*
 * @author josec
 * @project comogastoapp
 */
public class PlatformMetrics {
    private final MeterRegistry meterRegistry;

    public PlatformMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementBusinessEvent(String module, String eventType) {
        Counter.builder("comoGasto.business.events.total")
                .tag("module", module)
                .tag("eventType", eventType)
                .register(meterRegistry)
                .increment();
    }

    public void incrementSecurityEvent(String eventType, String result) {
        Counter.builder("comoGasto.security.events.total")
                .tag("eventType", eventType)
                .tag("result", result)
                .register(meterRegistry)
                .increment();
    }

    public void incrementAuditEvent(String module, String result) {
        Counter.builder("comoGasto.audit.events.total")
                .tag("module", module)
                .tag("result", result)
                .register(meterRegistry)
                .increment();
    }

    public void incrementTraceEvent(String stepType, String status) {
        Counter.builder("comoGasto.trace.steps.total")
                .tag("stepType", stepType)
                .tag("status", status)
                .register(meterRegistry)
                .increment();
    }
}
