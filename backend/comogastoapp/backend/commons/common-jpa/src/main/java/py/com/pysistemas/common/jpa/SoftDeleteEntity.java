package py.com.pysistemas.common.jpa;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/*
 * @author josec
 * @project comogastoapp
 */
@MappedSuperclass
public abstract class SoftDeleteEntity extends TenantEntity{
    @Column(name = "deleted", nullable = false)
    protected boolean deleted = false;

    @Column(name = "deleted_at")
    protected Instant deletedAt;

    @Column(name = "deleted_by")
    protected UUID deletedBy;

    public boolean isDeleted() {
        return deleted;
    }

    public void markDeleted(UUID userId) {
        this.deleted = true;
        this.deletedAt = Instant.now();
        this.deletedBy = userId;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public UUID getDeletedBy() {
        return deletedBy;
    }
}
