package py.com.pysistemas.common.inbox.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import py.com.pysistemas.common.inbox.status.InboxStatus;
import py.com.pysistemas.common.jpa.VersionedEntity;

/*
 * @author josec
 * @project comogastoapp
 */
@Entity
@Table(name = "inbox_events", schema = "inbox")
public class InboxEventEntity extends VersionedEntity {
    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    @Column(name = "event_type", nullable = false, length = 150)
    private String eventType;

    @Column(name = "consumer_name", nullable = false, length = 120)
    private String consumerName;

    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private String payload;

    @Column(name = "correlation_id", length = 80)
    private String correlationId;

    @Column(name = "trace_id", length = 80)
    private String traceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private InboxStatus status = InboxStatus.RECEIVED;

    @Column(name = "processed_at")
    private Instant processedAt;

    @Column(name = "last_error", columnDefinition = "text")
    private String lastError;

    public void markProcessing() {
        this.status = InboxStatus.PROCESSING;
    }

    public void markProcessed() {
        this.status = InboxStatus.PROCESSED;
        this.processedAt = Instant.now();
    }

    public void markFailed(String error) {
        this.status = InboxStatus.FAILED;
        this.lastError = error;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
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

    public InboxStatus getStatus() {
        return status;
    }

    public void setStatus(InboxStatus status) {
        this.status = status;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }
}
