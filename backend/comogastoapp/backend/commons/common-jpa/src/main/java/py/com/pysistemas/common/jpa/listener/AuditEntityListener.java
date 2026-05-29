package py.com.pysistemas.common.jpa.listener;

import java.time.Instant;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import py.com.pysistemas.common.jpa.AuditableEntity;
import py.com.pysistemas.commonsecurity.context.UserContext;

/*
 * @author josec
 * @project comogastoapp
 */
public class AuditEntityListener {
    @PrePersist
    public void beforePersist(AuditableEntity entity) {
        Instant now = Instant.now();

        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setCreatedBy(UserContext.getUserId());
        entity.setUpdatedBy(UserContext.getUserId());
    }

    @PreUpdate
    public void beforeUpdate(AuditableEntity entity) {
        entity.setUpdatedAt(Instant.now());
        entity.setUpdatedBy(UserContext.getUserId());
    }
}
