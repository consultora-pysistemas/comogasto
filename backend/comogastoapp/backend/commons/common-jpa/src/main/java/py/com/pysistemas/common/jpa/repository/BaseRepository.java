package py.com.pysistemas.common.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

import py.com.pysistemas.common.jpa.SoftDeleteEntity;

/*
 * @author josec
 * @project comogastoapp
 */
@NoRepositoryBean
public interface BaseRepository<T extends SoftDeleteEntity> extends JpaRepository<T, UUID> {
}
