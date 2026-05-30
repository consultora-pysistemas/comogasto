package py.com.pysistemas.common.inbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import py.com.pysistemas.common.inbox.entity.InboxEventEntity;

/*
 * @author josec
 * @project comogastoapp
 */
public interface InboxEventRepository extends JpaRepository<InboxEventEntity, UUID> {
    boolean existsByEventIdAndConsumerName(UUID eventId, String consumerName);

    Optional<InboxEventEntity> findByEventIdAndConsumerName(UUID eventId, String consumerName);

}
