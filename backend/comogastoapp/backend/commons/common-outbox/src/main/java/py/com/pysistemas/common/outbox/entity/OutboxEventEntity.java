package py.com.pysistemas.common.outbox.entity;

import java.time.Instant;

import jakarta.persistence.*;
import py.com.pysistemas.common.jpa.VersionedEntity;
import py.com.pysistemas.common.outbox.status.OutboxStatus;

/*
 * @author josec
 * @project comogastoapp
 */
@Entity
@Table(name = "outbox_events", schema = "outbox")
public class OutboxEventEntity extends VersionedEntity {
    @Column(name = "event_type", nullable = false, length = 150)
    private String eventType;

    @Column(name = "topic", nullable = false, length = 150)
    private String topic;

    @Column(name = "event_key", nullable = false, length = 120)
    private String eventKey;

    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private String payload;

    @Column(name = "correlation_id", length = 80)
    private String correlationId;

    @Column(name = "trace_id", length = 80)
    private String traceId;

    @Column(name = "idempotency_key", length = 160)
    private String idempotencyKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private OutboxStatus status = OutboxStatus.PENDING;

    @Column(name = "retry_count", nullable = false)
    private int retryCount = 0;

    @Column(name = "max_retries", nullable = false)
    private int maxRetries = 5;

    @Column(name = "last_error", columnDefinition = "text")
    private String lastError;

    @Column(name = "published_at")
    private Instant publishedAt;

    public void markPublished() {
        this.status = OutboxStatus.PUBLISHED;
        this.publishedAt = Instant.now();
    }

    public void markRetrying(String error) {
        this.status = OutboxStatus.RETRYING;
        this.retryCount++;
        this.lastError = error;
    }

    public void markFailed(String error) {
        this.status = OutboxStatus.FAILED;
        this.lastError = error;
    }

    public void markDeadLetter(String error) {
        this.status = OutboxStatus.DEAD_LETTER;
        this.lastError = error;
    }

    public boolean canRetry() {
        return retryCount < maxRetries;
    }
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public OutboxStatus getStatus() {
        return status;
    }

    public void setStatus(OutboxStatus status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }


}
