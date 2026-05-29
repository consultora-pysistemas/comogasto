package py.com.pysistemas.common.tracing.model;

/*
 * @author josec
 * @project comogastoapp
 */public enum TraceStatus {
    STARTED,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    PARTIALLY_FAILED,
    RETRIED,
    COMPENSATED
}
