package py.com.pysistemas.common.outbox.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.pysistemas.common.outbox.entity.OutboxEventEntity;
import py.com.pysistemas.common.outbox.repository.OutboxEventRepository;

/*
 * @author josec
 * @project comogastoapp
 */
@Service
public class OutboxService {
    private final OutboxEventRepository repository;

    public OutboxService(OutboxEventRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public OutboxEventEntity save(OutboxEventEntity event) {
        return repository.save(event);
    }
}
