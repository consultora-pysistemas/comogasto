package py.com.pysistemas.common.outbox.status;

/*
 * @author josec
 * @project comogastoapp
 */public enum OutboxStatus {
    PENDING,
    PUBLISHED,
    RETRYING,
    FAILED,
    DEAD_LETTER

}
