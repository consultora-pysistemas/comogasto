package py.com.pysistemas.common.tracing.context;

import java.util.UUID;

/*
 * @author josec
 * @project comogastoapp
 */
public final class TraceContext {
    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();
    private static final ThreadLocal<UUID> PROCESS_ID = new ThreadLocal<>();
    private static final ThreadLocal<UUID> CURRENT_STEP_ID = new ThreadLocal<>();

    private TraceContext() {
    }

    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static String getTraceId() {
        return TRACE_ID.get();
    }

    public static String getOrCreateTraceId() {
        if (TRACE_ID.get() == null) {
            TRACE_ID.set(UUID.randomUUID().toString());
        }

        return TRACE_ID.get();
    }

    public static void setProcessId(UUID processId) {
        PROCESS_ID.set(processId);
    }

    public static UUID getProcessId() {
        return PROCESS_ID.get();
    }

    public static UUID getOrCreateProcessId() {
        if (PROCESS_ID.get() == null) {
            PROCESS_ID.set(UUID.randomUUID());
        }

        return PROCESS_ID.get();
    }

    public static void setCurrentStepId(UUID stepId) {
        CURRENT_STEP_ID.set(stepId);
    }

    public static UUID getCurrentStepId() {
        return CURRENT_STEP_ID.get();
    }

    public static void clear() {
        TRACE_ID.remove();
        PROCESS_ID.remove();
        CURRENT_STEP_ID.remove();
    }
}
