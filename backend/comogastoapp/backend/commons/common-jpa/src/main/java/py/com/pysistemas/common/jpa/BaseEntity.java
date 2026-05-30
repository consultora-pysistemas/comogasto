package py.com.pysistemas.common.jpa;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

/*
 * @author josec
 * @project comogastoapp
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    protected UUID id;

    @PrePersist
    protected void ensureId() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
