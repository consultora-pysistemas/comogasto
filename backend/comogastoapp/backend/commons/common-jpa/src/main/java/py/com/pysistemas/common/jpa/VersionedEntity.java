package py.com.pysistemas.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

/*
 * @author josec
 * @project comogastoapp
 */
@MappedSuperclass
public abstract class VersionedEntity extends SoftDeleteEntity{
    @Version
    @Column(name = "version", nullable = false)
    protected Long version = 0L;

    public Long getVersion() {
        return version;
    }
}
