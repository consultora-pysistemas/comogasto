package py.com.pysistemas.common.tracing.model;

/*
 * @author josec
 * @project comogastoapp
 */public enum TraceStepType {
    HTTP_REQUEST,
    CONTROLLER,
    USE_CASE,
    SERVICE_METHOD,
    REPOSITORY_METHOD,
    DATABASE_OPERATION,
    KAFKA_PUBLISH,
    KAFKA_CONSUME,
    EXTERNAL_HTTP_CALL,
    AUDIT,
    NOTIFICATION,
    ERROR
}
