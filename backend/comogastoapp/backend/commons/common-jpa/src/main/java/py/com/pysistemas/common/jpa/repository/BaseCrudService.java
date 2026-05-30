package py.com.pysistemas.common.jpa.repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import py.com.pysistemas.common.jpa.SoftDeleteEntity;
import py.com.pysistemas.commonsecurity.context.UserContext;

/*
 * @author josec
 * @project comogastoapp
 */
public abstract class BaseCrudService<T extends SoftDeleteEntity> {
    protected abstract BaseRepository<T> repository();

    @Transactional
    public void softDelete(UUID id) {
        T entity = repository()
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity not found: " + id));

        entity.markDeleted(UserContext.getUserId());

        repository().save(entity);
    }
}
