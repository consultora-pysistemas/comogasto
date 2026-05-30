package py.com.pysistemas.common.outbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

import py.com.pysistemas.common.outbox.entity.OutboxEventEntity;
import py.com.pysistemas.common.outbox.status.OutboxStatus;

/*
 * @author josec
 * @project comogastoapp
 */
public interface  OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
    List<OutboxEventEntity> findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
