package py.com.pysistemas.common.outbox.scheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import py.com.pysistemas.common.events.envelope.EventEnvelope;
import py.com.pysistemas.common.events.publisher.EventPublisher;
import py.com.pysistemas.common.outbox.entity.OutboxEventEntity;
import py.com.pysistemas.common.outbox.repository.OutboxEventRepository;
import py.com.pysistemas.common.outbox.status.OutboxStatus;

/*
 * @author josec
 * @project comogastoapp
 */
public class OutboxPublisherScheduler {
    private final OutboxEventRepository repository;
    private final EventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public OutboxPublisherScheduler(
            OutboxEventRepository repository,
            EventPublisher eventPublisher, ObjectMapper objectMapper
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelayString = "${comogasto.outbox.publisher-delay-ms:5000}")
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEventEntity> events =
                repository.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);

        for (OutboxEventEntity event : events) {
            try {
                EventEnvelope<?> envelope = objectMapper.readValue(event.getPayload(), new TypeReference<EventEnvelope<?>>() {});
                eventPublisher.publish(event.getTopic(), envelope);
                event.markPublished();
            } catch (Exception ex) {
                if (event.canRetry()) {
                    event.markRetrying(ex.getMessage());
                } else {
                    event.markDeadLetter(ex.getMessage());
                }
            }

            repository.save(event);
        }
    }
}
