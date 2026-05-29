package py.com.pysistemas.common.inbox.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.pysistemas.common.events.envelope.EventEnvelope;
import py.com.pysistemas.common.inbox.entity.InboxEventEntity;
import py.com.pysistemas.common.inbox.repository.InboxEventRepository;

/*
 * @author josec
 * @project comogastoapp
 */
@Service
public class InboxService {
    private final InboxEventRepository repository;

    public InboxService(InboxEventRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public boolean alreadyProcessed(EventEnvelope<?> event, String consumerName) {
        return repository.existsByEventIdAndConsumerName(
                event.eventId(),
                consumerName
        );
    }

    @Transactional
    public void registerReceived(
            EventEnvelope<?> event,
            String consumerName,
            String payload
    ) {
        InboxEventEntity entity = new InboxEventEntity();

        entity.setEventId(event.eventId());
        entity.setEventType(event.eventType());
        entity.setConsumerName(consumerName);
        entity.setPayload(payload);
        entity.setCorrelationId(event.correlationId());
        entity.setTraceId(event.traceId());

        repository.save(entity);
    }

    @Transactional
    public void markProcessed(EventEnvelope<?> event, String consumerName) {
        repository.findByEventIdAndConsumerName(event.eventId(), consumerName)
                .ifPresent(inbox -> {
                    inbox.markProcessed();
                    repository.save(inbox);
                });
    }

    @Transactional
    public void markFailed(
            EventEnvelope<?> event,
            String consumerName,
            String error
    ) {
        repository.findByEventIdAndConsumerName(event.eventId(), consumerName)
                .ifPresent(inbox -> {
                    inbox.markFailed(error);
                    repository.save(inbox);
                });
    }
}
